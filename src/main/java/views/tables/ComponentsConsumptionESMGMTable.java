package views.tables;

import domain.InitializerForTest;
import domain.DetailElectrod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import views.tables.utils.CreateColumnForEMSAndESMGM;
import views.tables.utils.Searcher;

import java.util.List;

import static projectConstants.CustomConstants.ESMG_M;

public class ComponentsConsumptionESMGMTable {
    private TableView<DetailElectrod> table;
    private CreateColumnForEMSAndESMGM creator;
    public ComponentsConsumptionESMGMTable() {
        creator = new CreateColumnForEMSAndESMGM();
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
        table.getColumns().addAll(createColumns());
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        //delete after test
        List<DetailElectrod> de = Searcher.findProductInfo(InitializerForTest.getTestDE(),ESMG_M);
        table.getItems().addAll(de);

    }

    private ObservableList<TableColumn<DetailElectrod, ?>> createColumns() {

        List<TableColumn<DetailElectrod, ?>> columns = creator.createColumns(ESMG_M);
        return FXCollections.observableArrayList(columns);
    }



    public TableView<DetailElectrod> getTable() {
        return table;
    }
}
