package utils;

import domain.DetailElectrod;
import entities.Detail;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import projectConstants.CustomConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateColumnForEMSAndESMGM {

    public List<TableColumn<Detail, ?>> createColumns(DetailElectrod de) {

        TableColumn<Detail,Integer> id = new TableColumn<>("№ п/п");
        id.setCellValueFactory(value-> new SimpleObjectProperty<Integer>(value.getValue().getId()));

        TableColumn<Detail,String> title = new TableColumn<>("Название");
        title.setCellValueFactory(value->new SimpleStringProperty(value.getValue().getTitle()));

        TableColumn<Detail, Double> count = new TableColumn<>("Количество");
        count.setCellFactory(cell-> new TableCell<Detail, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.valueOf(item));
            }

        });
        count.setCellValueFactory(value-> new SimpleObjectProperty<Double>(de.getDetails().get(value.getValue()).keySet().iterator().next()));

        TableColumn<Detail,BigDecimal> cost = new TableColumn<>("Стоимость");
        cost.setEditable(true);
        cost.setCellFactory(cell-> new TableCell<Detail,BigDecimal>(){
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.toString());
            }
        });
        cost.setCellValueFactory(value->new SimpleObjectProperty<BigDecimal>(de.getDetails().get(value.getValue()).values().iterator().next()));

        return Arrays.asList(id, title, count, cost);
    }
}