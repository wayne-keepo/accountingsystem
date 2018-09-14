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
import utils.CreateColumnForESMGAndESMGM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponentsConsumptionESMGTable {
    private TableView<Detail> table;
    private ObservableList<Detail> details;
    private List<Integer> changes;
    private DetailElectrod detailElectrods;
    private CreateColumnForESMGAndESMGM creator;

    public ComponentsConsumptionESMGTable() {
        creator = new CreateColumnForESMGAndESMGM();
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
        table.setEditable(true);

        detailElectrods = DetailElectrodeService.getDEByType(
                DetailService.getAll(),
                CustomConstants.ESMG
        ).get(0);

        details = FXCollections.observableArrayList(new ArrayList<>(detailElectrods.getDetails().keySet()));

        if (details.size() > 10) changes = new ArrayList<>(details.size());
        else changes = new ArrayList<>();

        table.getColumns().addAll(createColumns());
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        table.setItems(details);
    }

    private ObservableList<TableColumn<Detail, ?>> createColumns() {
        return FXCollections.observableArrayList(creator.createColumns(detailElectrods, changes));
    }

    public void dataUpdate() {
        if (!changes.isEmpty())
            DetailElectrodeService.dataUpdate(detailElectrods, changes);
    }

    public DetailElectrod getDetailElectrods() {
        return detailElectrods;
    }

    public TableView<Detail> getTable() {
        return table;
    }
}
