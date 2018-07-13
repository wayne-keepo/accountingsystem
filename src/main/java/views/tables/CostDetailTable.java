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
import javafx.util.Callback;
import services.DetailService;

import java.math.BigDecimal;

public class CostDetailTable {
    private TableView<Detail> costDetailTable;
    private final DBDetailController dbController;

    private void createTable() {

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

        TableColumn<Detail, String> name = new TableColumn<>("Название");
        name.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Detail, Double> count = new TableColumn<>("Количество");
        count.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Detail, BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));


        TableColumn<Detail, String> descriptions = new TableColumn<>("Примечание");
        descriptions.setCellValueFactory(new PropertyValueFactory<>("descriptions"));


        return FXCollections.observableArrayList(id, name, count, cost, descriptions);
    }

    public TableView<Detail> getCostDetailTable() {
        return costDetailTable;
    }
}
