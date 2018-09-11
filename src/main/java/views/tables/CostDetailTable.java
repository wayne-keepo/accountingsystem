package views.tables;

import entities.Detail;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import services.DetailService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CostDetailTable {
    private TableView<Detail> costDetailTable;
    private ObservableList<Detail> details;
    private List<Integer> changes;

    private void createTable() {
        changes = new ArrayList<>(40);
        details = FXCollections.observableArrayList();
        costDetailTable = new TableView<>();

        costDetailTable.setEditable(true);
        costDetailTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        costDetailTable.getColumns().addAll(createColumns());

        details.addAll(DetailService.getAll());
        costDetailTable.setItems(details);
    }

    public CostDetailTable() {
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
        count.setCellValueFactory(cellData-> new SimpleObjectProperty<Double>(cellData.getValue().getCount()));
        count.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }
            @Override
            public Double fromString(String string) {
                return Double.valueOf(string);
            }
        }));

        TableColumn<Detail, BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(param -> new SimpleObjectProperty<BigDecimal>(param.getValue().getCost()));
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<BigDecimal>() {
            @Override
            public String toString(BigDecimal object) {
                return object.toString();
            }
            @Override
            public BigDecimal fromString(String string) {
                return new BigDecimal(string);
            }
        }));

        TableColumn<Detail, String> descriptions = new TableColumn<>("Примечание");
        descriptions.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
     //test
        descriptions.setOnEditCommit(t ->{
            System.out.println(t.getRowValue().getDescriptions()+" "+t.getNewValue());
        } );

        return FXCollections.observableArrayList(id, name, count, cost, descriptions);
    }

    public List<Integer> getChanges() {
        return changes;
    }

    public void clearChanges() {
        changes.clear();
    }

    public TableView<Detail> getCostDetailTable() {
        return costDetailTable;
    }

    public void refresh(){
        List<Detail> tmp = DetailService.getAll();
        details.clear();
        details.addAll(tmp);
        // пересмотреть
//        costDetailTable.setItems(details);


    }
}
