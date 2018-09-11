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

import java.util.ArrayList;

public class ComponentsConsumptionESMGMTable {
    private TableView<Detail> table;
    private CreateColumnForESMGAndESMGM creator;
    private ObservableList<Detail> details;
    private DetailElectrod detailElectrods;

    public ComponentsConsumptionESMGMTable() {
        creator = new CreateColumnForESMGAndESMGM();
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
        table.setEditable(true);
        detailElectrods = DetailElectrodeService.getDEByType(
                DetailService.getAll(),
                CustomConstants.ESMG_M
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

    public DetailElectrod getDetailElectrods() {
        return detailElectrods;
    }
}
//            System.out.println(String.format("%s : \n Keys: %s \n %s \n Inc = %d Id = %d Inc>Size : %b--------------","ESMG-M", Arrays.toString(keys),value.getValue().toString(),i.initRawElectrodeValue(),tmp,i.initRawElectrodeValue()>size));
