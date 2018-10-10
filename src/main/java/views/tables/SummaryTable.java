package views.tables;

import entities.Summary;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import projectConstants.CustomConstants;
import services.SummaryService;

import java.time.LocalDate;
import java.util.List;

public class SummaryTable {

    private TableView<Summary> table;
    private ObservableList<Summary> es = FXCollections.observableArrayList();

    public SummaryTable() {
        createTable();
    }

    private void createTable() {
        ObservableList<Summary> initial = SummaryService.getAllAsObservableList();
        if (!initial.isEmpty())
            es.addAll(initial);
        table = new TableView<>();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(createColumns());
        table.setItems(es);
    }

    private ObservableList<TableColumn<Summary, ?>> createColumns() {
        TableColumn<Summary, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getIdSummary()).asObject());

        TableColumn<Summary, String> electrodNumber = new TableColumn<>("№ электрода");
        electrodNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getElectrodeNumber()));

        TableColumn<Summary, LocalDate> produceDate = new TableColumn<>("Дата производства");
        produceDate.setCellValueFactory(param -> new SimpleObjectProperty<LocalDate>(param.getValue().getProduceDate()));

        TableColumn<Summary, String> customer = new TableColumn<>("Заказчик");
        customer.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCustomer()));

        TableColumn<Summary, String> consumeDate = new TableColumn<>("Дата отгрузки");
        consumeDate.setCellFactory(TextFieldTableCell.forTableColumn());
        consumeDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getConsumeDate().toString()));
        consumeDate.setOnEditCommit(event -> {
            Summary summary = event.getRowValue();
            LocalDate newDate = LocalDate.parse(event.getNewValue(),CustomConstants.DATE_TIME_FORMATTER);
            summary.setConsumeDate(newDate);
            SummaryService.update(summary);
        });


        TableColumn<Summary, String> note = new TableColumn<>("Примечание");
        note.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNote()));

        return FXCollections.observableArrayList(id, electrodNumber, produceDate, customer, consumeDate, note);
    }

    public TableView<Summary> getTable() {
        return table;
    }
// TODO: переделать обновление, добавлять только новые а не пересоздавать!!!
    public void refresh() {
        es.clear();
        es.addAll(SummaryService.getAllAsObservableList());
//        for (Summary summary: SummaryService.getAllAsObservableList()){
//            if (!es.contains(summary))
//                es.add(summary);
//        }
//        table.getItems().clear();
//        table.getItems().addAll(SummaryService.getAllAsObservableList());
    }

    public void deleteSummary(Summary summary) {
        es.remove(summary);
    }

    public List<Summary> getSummaries() {
        return es;
    }
}
