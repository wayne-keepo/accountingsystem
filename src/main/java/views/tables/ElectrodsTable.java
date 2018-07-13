package views.tables;

import domain.Electrod;
import domain.ElectrodeSummary;
import domain.InitializerForTest;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import services.ElectrodeService;

import javax.swing.plaf.PanelUI;
import java.time.LocalDate;

public class ElectrodsTable {

    private TableView<ElectrodeSummary> table;
    private ObservableList<ElectrodeSummary> es = FXCollections.observableArrayList();

    public ElectrodsTable(){createTable();}

    private void createTable(){
        ObservableList<ElectrodeSummary> initial = ElectrodeService.buildElectrodeSummary();
        if (initial!=null)
            es.addAll(initial);
        table = new TableView<>();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(createColumns());
        table.getItems().addAll(es);
    }

    private ObservableList<TableColumn<ElectrodeSummary,?>> createColumns(){
        TableColumn<ElectrodeSummary,Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ElectrodeSummary, Integer> param) {
                return new SimpleIntegerProperty(
                        param.getValue().getElectrod().getId()
                ).asObject();
            }
        });

        TableColumn<ElectrodeSummary,String> electrodNumber = new TableColumn<>("№ электрода");
        electrodNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ElectrodeSummary, String> param) {
                return new SimpleStringProperty(param.getValue().getElectrod().getElectrodNumber());
            }
        });

        TableColumn<ElectrodeSummary,LocalDate> produceDate = new TableColumn<>("Дата производства");
        produceDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<ElectrodeSummary, LocalDate> param) {
                return new SimpleObjectProperty<LocalDate>(
                        param.getValue().getSummary().getProduceDate());
            }
        });

        TableColumn<ElectrodeSummary,String> customer = new TableColumn<>("Заказчик");
        customer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ElectrodeSummary, String> param) {
                return new SimpleStringProperty(
                        param.getValue().getSummary().getCustomer()
                );
            }
        });

        TableColumn<ElectrodeSummary,LocalDate> consumeDate = new TableColumn<>("Дата отгрузки");
        consumeDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<ElectrodeSummary, LocalDate> param) {
                return new SimpleObjectProperty<LocalDate>(
                        param.getValue().getSummary().getConsumeDate()
                );
            }
        });

        TableColumn<ElectrodeSummary,String> note = new TableColumn<>("Примечание");
        note.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ElectrodeSummary, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ElectrodeSummary, String> param) {
                return new SimpleStringProperty(
                        param.getValue().getSummary().getNote()
                );
            }
        });

        return FXCollections.observableArrayList(id,electrodNumber,produceDate,customer,consumeDate,note);
    }

    public TableView<ElectrodeSummary> getTable() {
        return table;
    }

    public void refresh(){
        table.getItems().clear();
        table.getItems().addAll(ElectrodeService.buildElectrodeSummary());
    }
}
