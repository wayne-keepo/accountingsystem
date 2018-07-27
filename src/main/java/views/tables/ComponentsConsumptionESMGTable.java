package views.tables;

import domain.DetailElectrod;
import entities.Detail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import projectConstants.CustomConstants;
import services.DetailElectrodeService;
import services.DetailService;
import utils.CreateColumnForEMSAndESMGM;

import java.util.ArrayList;

public class ComponentsConsumptionESMGTable {
    private TableView<Detail> table;
    private CreateColumnForEMSAndESMGM creator;
    private ObservableList<Detail> details;
    private DetailElectrod detailElectrods;

    public ComponentsConsumptionESMGTable() {
        creator = new CreateColumnForEMSAndESMGM();
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
        detailElectrods = DetailElectrodeService.getDEByType(
                DetailService.getAll(),
                CustomConstants.ESMG
        ).get(0);
        details = FXCollections.observableArrayList(new ArrayList<>(detailElectrods.getDetails().keySet()));
        table.getColumns().addAll(createColumns());
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getItems().addAll(details);

    }

    private ObservableList<TableColumn<Detail, ?>> createColumns() {
        return FXCollections.observableArrayList(creator.createColumns(detailElectrods));
    }

    public TableView<Detail> getTable() {
        return table;
    }
}
