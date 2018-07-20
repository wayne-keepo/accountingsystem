package views.tables;

import databaselogic.controllers.DBDetailController;
import entities.Detail;
import domain.InitializerForTest;
import javafx.beans.property.SimpleObjectProperty;
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CostDetailTable {
    private TableView<Detail> costDetailTable;
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
        count.setCellFactory(new Callback<TableColumn<Detail, Double>, TableCell<Detail, Double>>() {
            @Override
            public TableCell<Detail, Double> call(TableColumn<Detail, Double> param) {
                return new TableCell<Detail,Double>(){
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item!=null)
                            setText(String.valueOf(
                                    new BigDecimal(item).setScale(3, RoundingMode.UP).toString()
                            ));
                    }
                };
            }
        });
        count.setOnEditCommit(event -> {
            Double value = event.getNewValue();
            event.getRowValue().setCount(value);
            int chId = event.getRowValue().getId();
            if (!changes.contains(chId))
                changes.add(chId);
        });
        count.setEditable(true);

        TableColumn<Detail, BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(data->new SimpleObjectProperty<BigDecimal>(data.getValue().getCost()));
        cost.setCellFactory(new Callback<TableColumn<Detail, BigDecimal>, TableCell<Detail, BigDecimal>>() {
            @Override
            public TableCell<Detail, BigDecimal> call(TableColumn<Detail, BigDecimal> param) {
                return new TableCell<Detail,BigDecimal>(){
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item!=null)
                            setText(item.toString());
                    }
                };
            }
        });
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

    public void clearChanges() {
        changes.clear();
    }

    public TableView<Detail> getCostDetailTable() {
        return costDetailTable;
    }
}
