package views.tables;

import entities.Detail;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import services.DetailService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CostDetailTable {
    private TableView<Detail> costDetailTable;
    private ObservableList<Detail> details;
    private List<Integer> changes;

    public CostDetailTable() {
        createTable();
    }

    private void createTable() {
        changes = new ArrayList<>();
        details = FXCollections.observableArrayList();
        List<Detail> tmp = DetailService.getAll();
        if (tmp!=null)
            details.addAll(tmp);
        costDetailTable = new TableView<>();

        costDetailTable.setEditable(true);
        costDetailTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        costDetailTable.getColumns().addAll(createColumns());

        costDetailTable.setItems(details);
    }

    private ObservableList<TableColumn<Detail, ?>> createColumns() {

        TableColumn<Detail, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);

        TableColumn<Detail, String> name = new TableColumn<>("Название");
        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Detail tmp = event.getRowValue();
            tmp.setTitle(event.getNewValue());
            catchChanges(tmp.getId());
        });

        TableColumn<Detail, String> count = new TableColumn<>("Количество");
        count.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCount())));
        count.setCellFactory(TextFieldTableCell.forTableColumn());
        count.setOnEditCommit(event -> {
            Detail tmp = event.getRowValue();
            tmp.setCount(Double.valueOf(event.getNewValue()));
            catchChanges(tmp.getId());
        });

        TableColumn<Detail, String> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCost().toString()));
        cost.setCellFactory(TextFieldTableCell.forTableColumn());
        cost.setOnEditCommit(event -> {
            Detail tmp = event.getRowValue();
            tmp.setCost(new BigDecimal(event.getNewValue()));
            catchChanges(tmp.getId());
        });

        TableColumn<Detail, String> descriptions = new TableColumn<>("Примечание");
        descriptions.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptions.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        descriptions.setOnEditCommit(event -> {
            Detail tmp = event.getRowValue();
            tmp.setDescriptions(event.getNewValue());
            catchChanges(tmp.getId());
        });

        return FXCollections.observableArrayList(id, name, count, cost, descriptions);
    }

    public void refresh() {
        List<Detail> tmp = DetailService.getAll();
        details.clear();
        details.addAll(tmp);
    }

    private void catchChanges(Integer id) {
        if (!changes.contains(id))
            changes.add(id);
    }

    public void dataUpdate() {
        if (!changes.isEmpty()) {
            DetailService.sendOnUpdate(details, changes);
            clearChanges();
        }
    }

    public void clearChanges() {
        changes.clear();
    }

    public void addDetail(Detail detail){
        details.add(detail);
    }

    public void removeDetail(Detail detail){
        details.remove(detail);
    }

    public List<Integer> getChanges() {
        return changes;
    }

    public TableView<Detail> getCostDetailTable() {
        return costDetailTable;
    }
}
