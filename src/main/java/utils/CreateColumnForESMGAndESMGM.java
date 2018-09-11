package utils;

import domain.DetailElectrod;
import entities.Detail;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import projectConstants.CustomConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateColumnForESMGAndESMGM {

    public List<TableColumn<Detail, ?>> createColumns(DetailElectrod de) {

        TableColumn<Detail, Integer> id = new TableColumn<>("№ п/п");
        id.setCellValueFactory(value -> new SimpleObjectProperty<Integer>(value.getValue().getId()));

        TableColumn<Detail, String> title = new TableColumn<>("Название");
        title.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTitle()));

        TableColumn<Detail, Double> count = new TableColumn<>("Количество");
        count.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.valueOf(object);
            }
            @Override
            public Double fromString(String string) {
                return Double.valueOf(string);
            }
        }));
        count.setCellValueFactory(param -> new SimpleObjectProperty<Double>(de.getDetails().get(param.getValue()).keySet().iterator().next()));

//         вернуться к закоменченному когда придумаю апдейт значений в бд, сейчас не хочу, спать хочу.
//        count.setOnEditCommit(event -> {
//            System.out.println("Set detail count: old value: " + event.getOldValue() + " new value: " + event.getNewValue());
//            event.getTableView().getItems()
//                    .get(
//                            event.getTablePosition().getRow()
//                    ).setCount(Double.valueOf(event.getNewValue()));
//        });


        TableColumn<Detail, BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setEditable(true);
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<BigDecimal>() {
            @Override
            public String toString(BigDecimal object) {
                return object.toString();
            }
            @Override
            public BigDecimal fromString(String string) {
                return new BigDecimal(string);
            }
        }));
        cost.setCellValueFactory(value -> new SimpleObjectProperty<BigDecimal>(de.getDetails().get(value.getValue()).values().iterator().next()));

        return Arrays.asList(id, title, count, cost);
    }
}
