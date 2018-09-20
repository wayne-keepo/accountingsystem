package views.tables;

import entities.RawElectrode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.ElectrodeService;
import utils.Types;

public class RawElectrodeTable {
    private TableView<RawElectrode> table = null;
    private ObservableList<RawElectrode> electrodes;

    public RawElectrodeTable(){
        create();
    }

    private void create(){
        table = new TableView<>();
        electrodes = ElectrodeService.getAllAsObservableList();
        table.setItems(electrodes);
        createColumns();
    }

    private void createColumns(){
        TableColumn<RawElectrode,Integer> id = new TableColumn<>("№");
        id.setCellValueFactory(param -> new SimpleObjectProperty<Integer>(param.getValue().getId()));

        TableColumn<RawElectrode,String> type = new TableColumn<>("Тип");
        type.setCellValueFactory(param -> new SimpleStringProperty(Types.valueOf(param.getValue().getType().replace("-","_")).ru()));

        TableColumn<RawElectrode,Integer> count = new TableColumn<>("Количество");
        count.setCellValueFactory(param -> new SimpleObjectProperty<Integer>(param.getValue().getCount()));

        table.getColumns().addAll(id,type,count);
    }

    public TableView<RawElectrode> getTable() {
        return table;
    }
    // TODO: review?
    public void refresh(){
        ObservableList<RawElectrode> rws = ElectrodeService.getAllAsObservableList();
        electrodes.clear();
        electrodes.addAll(rws);
    }
}
