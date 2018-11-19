package views.tables;

import entities.RawElectrode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import services.ElectrodeService;
import utils.converter.IntegerConverter;
import utils.enums.Types;

public class RawElectrodeTable {
    private TableView<RawElectrode> table;
    private ObservableList<RawElectrode> electrodes;

    public RawElectrodeTable(){
        create();
    }

    private void create(){
        table = new TableView<>();
        table.setEditable(true);
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
        count.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerConverter()));
        count.setCellValueFactory(param -> new SimpleObjectProperty<Integer>(param.getValue().getCount()));
        count.setOnEditCommit(event -> {
            RawElectrode tmp = event.getRowValue();
            tmp.setCount(event.getNewValue());
            ElectrodeService.updateRawElectrodeCount(tmp,tmp.getCount());
        });

        table.getColumns().addAll(id,type,count);
    }

    public TableView<RawElectrode> getTable() {
        return table;
    }
// we can use as this because small RW data in DB
    public void refresh(){
        ObservableList<RawElectrode> rws = ElectrodeService.getAllAsObservableList();
        electrodes.clear();
        electrodes.addAll(rws);
    }
}
