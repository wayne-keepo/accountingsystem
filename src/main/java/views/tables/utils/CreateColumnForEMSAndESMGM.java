package views.tables.utils;

import domain.DetailElectrod;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import projectConstants.CustomConstants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CreateColumnForEMSAndESMGM {

    public List<TableColumn<DetailElectrod, ?>> createColumns(String type) {
        TableColumn<DetailElectrod, Integer> id = new TableColumn<>("№ п/п");
        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DetailElectrod, Integer> param) {
//                int id = param.getValue().getDetail().getId();
                return new SimpleObjectProperty<Integer>(null);
            }
        });

        TableColumn<DetailElectrod, String> title = new TableColumn<>("Название");
        title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DetailElectrod, String> param) {
//                String title = param.getValue().getDetail().getTitle();
                return new SimpleStringProperty(null);
            }
        });
        TableColumn<DetailElectrod, BigDecimal> cost = null;
        TableColumn<DetailElectrod, Integer> electrod = null;
        if (type == CustomConstants.ESMG) {
            electrod = new TableColumn<>("ЭСМГ");
            electrod.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DetailElectrod, Integer> param) {
                    DetailElectrod de = param.getValue();
                    return new SimpleObjectProperty<Integer>(null);
                }
            });

            cost = new TableColumn<>("Цена");
            cost.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, BigDecimal>, ObservableValue<BigDecimal>>() {
                @Override
                public ObservableValue<BigDecimal> call(TableColumn.CellDataFeatures<DetailElectrod, BigDecimal> param) {
//                    BigDecimal cost = param.getValue().getDetail().getCost();
                    return new SimpleObjectProperty<BigDecimal>(null);
                }
            });
        }
        if (type == CustomConstants.ESMG_M) {
            electrod = new TableColumn<>("ЭСМГ-М");
            electrod.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DetailElectrod, Integer> param) {
                    DetailElectrod de = param.getValue();
                    return new SimpleObjectProperty<Integer>(null);

                }
            });
            cost = new TableColumn<>("Цена");
            cost.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetailElectrod, BigDecimal>, ObservableValue<BigDecimal>>() {
                @Override
                public ObservableValue<BigDecimal> call(TableColumn.CellDataFeatures<DetailElectrod, BigDecimal> param) {
//                    BigDecimal cost = param.getValue().getDetail().getCost();
                    return new SimpleObjectProperty<BigDecimal>(null);
                }
            });
        }

        return Arrays.asList(id, title, electrod, cost);
    }
}
