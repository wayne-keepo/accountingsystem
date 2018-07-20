package views.tables;

import databaselogic.controllers.DBDetailController;
import entities.Detail;
import domain.InitializerForTest;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import services.DetailService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CostDetailTable {
    private TableView<Detail> costDetailTable;
    private final DBDetailController dbController;
    private List<Integer> changes;

    private void createTable() {
        changes = new ArrayList<>();
        costDetailTable = new TableView<>();
        costDetailTable.setEditable(true);
        costDetailTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        costDetailTable.getColumns().addAll(createColumns());

        costDetailTable.getItems().addAll(FXCollections.observableArrayList(DetailService.getAll()));
    }

    public CostDetailTable() {
        dbController = new DBDetailController();
        createTable();
    }

    private ObservableList<TableColumn<Detail, ?>> createColumns() {

        TableColumn<Detail, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);

        TableColumn<Detail, String> name = new TableColumn<>("Название");
        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            String value = event.getNewValue();
            int chId = event.getRowValue().getId();
            event.getRowValue().setTitle(value);
            if (!changes.contains(chId))
                changes.add(chId);
        });

        TableColumn<Detail, Double> count = new TableColumn<>("Количество");
        count.setCellValueFactory(new PropertyValueFactory<>("count"));

        count.setOnEditCommit(event -> {
            Double value = event.getNewValue();
            event.getRowValue().setCount(value);
            int chId = event.getRowValue().getId();
            if (!changes.contains(chId))
                changes.add(chId);
        });

        TableColumn<Detail, BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        cost.setOnEditCommit(event -> {
            BigDecimal value = event.getNewValue();
            event.getRowValue().setCost(value);
            int chId = event.getRowValue().getId();
            if (!changes.contains(chId))
                changes.add(chId);
        });

        TableColumn<Detail, String> descriptions = new TableColumn<>("Примечание");
        descriptions.setCellValueFactory(new PropertyValueFactory<>("descriptions"));


        return FXCollections.observableArrayList(id, name, count, cost, descriptions);
    }

    public List<Integer> getChanges() {
        return changes;
    }
    public  void clearChanges(){
        changes.clear();
    }
    public TableView<Detail> getCostDetailTable() {
        return costDetailTable;
    }
}
