package views.tables;

import domain.Electrod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ElectrodeService;

public class CreateElectrodeTable {
    private TableView<Electrod> table;
    private ObservableList<Electrod> electrods;

    public CreateElectrodeTable(){create();}

    private void create(){
        electrods = ElectrodeService.getAll();
        table = new TableView<>();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(createColumn());
        table.getItems().addAll(electrods);
    }

    private ObservableList<TableColumn<Electrod,?>> createColumn(){
        TableColumn<Electrod,Integer>  id = new TableColumn<>("№ п/п");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Electrod,String>   number  = new TableColumn<>("Номер");
        number.setCellValueFactory(new PropertyValueFactory<>("electrodNumber"));

        TableColumn<Electrod,String>   type  = new TableColumn<>("Тип");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        return FXCollections.observableArrayList(id,number,type);
    }

    public TableView<Electrod> getTable() {
        return table;
    }

    public ObservableList<Electrod> getElectrods() {
        return electrods;
    }

    public void setElectrods(ObservableList<Electrod> electrods) {
        this.electrods = electrods;
    }
}
