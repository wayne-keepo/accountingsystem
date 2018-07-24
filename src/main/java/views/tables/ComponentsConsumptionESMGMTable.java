package views.tables;

import databaselogic.utils.ChainUtil;
import domain.InitializerForTest;
import domain.DetailElectrod;
import entities.Detail;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import projectConstants.CustomConstants;
import services.DetailElectrodeService;
import services.DetailService;
import views.tables.utils.CreateColumnForEMSAndESMGM;
import views.tables.utils.Searcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static projectConstants.CustomConstants.ESMG_M;

public class ComponentsConsumptionESMGMTable {
    private TableView<Detail> table;
    private CreateColumnForEMSAndESMGM creator;
    private ObservableList<Detail> details;
    private DetailElectrod detailElectrods;

    public ComponentsConsumptionESMGMTable() {
        creator = new CreateColumnForEMSAndESMGM();
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
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
}
//            System.out.println(String.format("%s : \n Keys: %s \n %s \n Inc = %d Id = %d Inc>Size : %b--------------","ESMG-M", Arrays.toString(keys),value.getValue().toString(),i.get(),tmp,i.get()>size));
