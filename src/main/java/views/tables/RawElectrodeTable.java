package views.tables;

import entities.RawElectrode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ElectrodeService;

import java.util.List;


public class RawElectrodeTable {
    private TableView<RawElectrode> table = null;
    private ObservableList<RawElectrode> electrodes;

    public RawElectrodeTable(){
        create();
    }

    private void create(){
        table = new TableView<>();
        // TODO: check on null
        electrodes = ElectrodeService.getAllAsObservableList();
        table.setItems(electrodes);
        createColumns();
    }

    private void createColumns(){
        TableColumn<RawElectrode,Integer> id = new TableColumn<>("№");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<RawElectrode,String> type = new TableColumn<>("Тип");
        id.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<RawElectrode,Integer> count = new TableColumn<>("Количество");
        id.setCellValueFactory(param -> new SimpleObjectProperty<Integer>(param.getValue().getCount()));

        table.getColumns().addAll(id,type,count);
    }

    public TableView<RawElectrode> getTable() {
        return table;
    }
    // TODO: review?
    public void refresh(ObservableList<RawElectrode> rws){
        rws.forEach(raw->{if (!electrodes.contains(raw)) electrodes.add(raw);});
    }
}
