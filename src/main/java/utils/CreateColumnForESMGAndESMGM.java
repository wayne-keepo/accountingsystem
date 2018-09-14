package utils;

import domain.DetailElectrod;
import entities.Detail;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CreateColumnForESMGAndESMGM {

    public List<TableColumn<Detail, ?>> createColumns(DetailElectrod de,List<Integer> changes) {

        TableColumn<Detail, Integer> id = new TableColumn<>("№ п/п");
        id.setEditable(false);
        id.setCellValueFactory(value -> new SimpleObjectProperty<Integer>(value.getValue().getId()));

        TableColumn<Detail, String> title = new TableColumn<>("Название");
        title.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTitle()));
        title.setEditable(false);

        TableColumn<Detail, String> count = new TableColumn<>("Количество");
        count.setCellFactory(TextFieldTableCell.forTableColumn());
        count.setCellValueFactory(param -> new SimpleStringProperty(
                String.valueOf(
                        de.getDetails().get(param.getValue()).keySet().iterator().next()
                )));
        count.setOnEditCommit(event -> {
            System.out.println("Init update count for produce");

            Double newKey = Double.valueOf(event.getNewValue());
            System.out.println("New key count: "+newKey);

            Detail tmp = event.getRowValue();
            System.out.println("Upd detail: "+tmp.getTitle());

            Map<Double,BigDecimal> map = de.getDetails().get(tmp);
            System.out.println("Map for update: "+map.toString());

            Double oldKey = map.keySet().iterator().next();
            System.out.println("Old key count: "+oldKey);

            System.out.println("Replace old key "+oldKey+" on new key "+newKey);
            map.put(newKey,map.remove(oldKey));
            System.out.println("New map result: "+map.toString());

            System.out.println("Check map into DE obj: "+de.getDetails().get(tmp).toString());
            catchUpdate(changes,tmp.getId());
            System.out.println("Check changes list: "+changes.toString());
        });

        TableColumn<Detail, String> cost = new TableColumn<>("Стоимость");
        cost.setEditable(true);
        cost.setCellFactory(TextFieldTableCell.forTableColumn());
        cost.setCellValueFactory(value -> new SimpleStringProperty(de.getDetails().get(value.getValue()).values().iterator().next().toString()));
        cost.setOnEditCommit(event -> {
            System.out.println("Init update cost for produce");

            BigDecimal newCost = new BigDecimal(event.getNewValue());
            System.out.println("New cost value : "+newCost);

            Detail tmp = event.getRowValue();
            System.out.println("Upd detail: "+tmp.getTitle());

            Map<Double,BigDecimal> map = de.getDetails().get(tmp);
            System.out.println("Map for update: "+map.toString());
            Double key = map.keySet().iterator().next();
            BigDecimal oldCost = map.values().iterator().next();
            System.out.println("Old cost value: "+oldCost);
            System.out.println("Replace old cost "+oldCost+" on new cost "+newCost);

            map.replace(key,oldCost,newCost);
            System.out.println("New map result: "+map.toString());

            System.out.println("Check map into DE obj: "+de.getDetails().get(tmp).toString());
            catchUpdate(changes,tmp.getId());
            System.out.println("Check changes list: "+changes.toString());
        });

        return Arrays.asList(id, title, count, cost);
    }
    private void catchUpdate(List<Integer> changes,Integer id){
        if (!changes.contains(id))
            changes.add(id);
        else System.out.println("This id = "+id+" already contains in list for update.");
    }
}
