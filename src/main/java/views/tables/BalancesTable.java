package views.tables;

import domain.Balance;
import entities.Detail;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import projectConstants.CustomConstants;
import services.BalanceService;
import utils.Searcher;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BalancesTable {
    private TableView<Balance> table;
    private ObservableList<Balance> balances = FXCollections.observableArrayList();

    public BalancesTable() {
        createTable();
    }

    private void createTable() {
        table = new TableView<>();
        initializingDataInTable();
        settings();
    }

    private void settings() {
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(createColumn());
        table.getItems().addAll(balances);
    }

    private void initializingDataInTable() {
        ObservableList<Balance> initialBalances = BalanceService.buildBalances();
//        System.out.println("Balances from BalanceTable#initializingDataInTable()\n"+initialBalances.toString());
        if (initialBalances != null)
            balances.addAll(initialBalances); // продумать как быть при самом первом запуске, падает ошибка если в базе нет данных по балансу!! (+/-) протестировать изменения
    }

    private List<TableColumn<Balance, ?>> createColumn() {

        TableColumn<Balance, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Balance, String> title = new TableColumn<>("Название");
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetail().getTitle()));
        title.setCellFactory(TextFieldTableCell.<Balance>forTableColumn());
        title.setOnEditCommit(event -> {
            event.getRowValue().getDetail().setTitle(event.getNewValue());
        });

        TableColumn<Balance, Year> year = new TableColumn<>(Year.now().toString());

        TableColumn<Balance, Integer> incoming = new TableColumn<>("Приход");
        incoming.setCellValueFactory(new PropertyValueFactory<>("incoming"));

        TableColumn<Balance, Integer> outcoming = new TableColumn<>("Расход");
        outcoming.setCellValueFactory(new PropertyValueFactory<>("outcoming"));

        TableColumn<Balance, Integer> atBeginingYear = new TableColumn<>("На начало года");
        atBeginingYear.setCellValueFactory(new PropertyValueFactory<>("balanceAtBeginningYear"));

        TableColumn<Balance, Integer> atEndingYear = new TableColumn<>("На конец года");
        atEndingYear.setCellValueFactory(new PropertyValueFactory<>("balanceAtEndOfYear"));

        //month's in
        TableColumn<Balance, String> inJanuary = new TableColumn<>("Январь");
        TableColumn<Balance, String> inFebruary = new TableColumn<>("Февраль");
        TableColumn<Balance, String> inMarch = new TableColumn<>("Март");
        TableColumn<Balance, String> inApril = new TableColumn<>("Апрель");
        TableColumn<Balance, String> inMay = new TableColumn<>("Май");
        TableColumn<Balance, String> inJune = new TableColumn<>("Июнь");
        TableColumn<Balance, String> inJuly = new TableColumn<>("Июль");
        TableColumn<Balance, String> inAugust = new TableColumn<>("Август");
        TableColumn<Balance, String> inSeptember = new TableColumn<>("Сентябрь");
        TableColumn<Balance, String> inOctober = new TableColumn<>("Октябрь");
        TableColumn<Balance, String> inNovember = new TableColumn<>("Ноябрь");
        TableColumn<Balance, String> inDecember = new TableColumn<>("Декабрь");
        TableColumn<Balance, String> inTotal = new TableColumn<>("Итог");
        inTotal.setCellValueFactory(new PropertyValueFactory<>("inTotal"));
        //month's  out
        TableColumn<Balance, String> outJanuary = new TableColumn<>("Январь");
        TableColumn<Balance, String> outFebruary = new TableColumn<>("Февраль");
        TableColumn<Balance, String> outMarch = new TableColumn<>("Март");
        TableColumn<Balance, String> outApril = new TableColumn<>("Апрель");
        TableColumn<Balance, String> outMay = new TableColumn<>("Май");
        TableColumn<Balance, String> outJune = new TableColumn<>("Июнь");
        TableColumn<Balance, String> outJuly = new TableColumn<>("Июль");
        TableColumn<Balance, String> outAugust = new TableColumn<>("Август");
        TableColumn<Balance, String> outSeptember = new TableColumn<>("Сентябрь");
        TableColumn<Balance, String> outOctober = new TableColumn<>("Октябрь");
        TableColumn<Balance, String> outNovember = new TableColumn<>("Ноябрь");
        TableColumn<Balance, String> outDecember = new TableColumn<>("Декабрь");
        TableColumn<Balance, String> outTotal = new TableColumn<>("Итог");
        outTotal.setCellValueFactory(new PropertyValueFactory<>("outTotal"));

        //приход
        incoming.getColumns().addAll(atBeginingYear, inJanuary, inFebruary, inMarch, inApril, inMay, inJune, inJuly, inAugust, inSeptember, inOctober, inNovember, inDecember, inTotal);
        //расход
        outcoming.getColumns().addAll(outJanuary, outFebruary, outMarch, outApril, outMay, outJune, outJuly, outAugust, outSeptember, outOctober, outNovember, outDecember, outTotal, atEndingYear);

        year.getColumns().addAll(id, title, incoming, outcoming);

        List<TableColumn<Balance, String>> months = Arrays.asList(
                inJanuary, inFebruary, inMarch, inApril, inMay, inJune, inJuly, inAugust, inSeptember, inOctober, inNovember, inDecember,
                outJanuary, outFebruary, outMarch, outApril, outMay, outJune, outJuly, outAugust, outSeptember, outOctober, outNovember, outDecember);

        setBulkCellValueFactoryForMonthColumn(months);

        colorCell(Arrays.asList(atBeginingYear,inJanuary, inFebruary, inMarch, inApril, inMay, inJune, inJuly, inAugust, inSeptember, inOctober, inNovember, inDecember, inTotal), CustomConstants.GREEN);
        colorCell(Arrays.asList(outJanuary, outFebruary, outMarch, outApril, outMay, outJune, outJuly, outAugust, outSeptember, outOctober, outNovember, outDecember, outTotal,atEndingYear),CustomConstants.YELLOW);
        return Arrays.asList(year);
    }

    private void setBulkCellValueFactoryForMonthColumn(List<TableColumn<Balance, String>> columns) {
        for (TableColumn column : columns)
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures param) {
                    String parentColumnName = param.getTableColumn().getParentColumn().getText();
                    String columnName = param.getTableColumn().getText();
                    Balance balance = (Balance) param.getValue();
                    Double count = Searcher.findValueByMonth(balance, columnName, parentColumnName);
                    count = new BigDecimal(count).setScale(3, RoundingMode.UP).doubleValue();
                    return new SimpleStringProperty(Double.toString(count));
                }
            });
    }

    private void colorCell(List<TableColumn> columns, String style) {
        columns.forEach(column -> {
            column.setCellFactory(cell-> new TableCell<Object,Object>(){
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item!=null) {
                        setText(String.valueOf(item));
                        setStyle(style);
                    }
                }
            });
        });
    }

    public ObservableList<Balance> getBalances() {
        return balances;
    }

    public List<Detail> getDetails(){
        List<Detail> details = new ArrayList<>();
        balances.forEach(balance -> details.add(balance.getDetail()));
        if (details.isEmpty())
            return null;
        return details;
    }

    public TableView<Balance> getTable() {
        return table;
    }
}
