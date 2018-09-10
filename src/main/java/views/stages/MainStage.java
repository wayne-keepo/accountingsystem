package views.stages;

import databaselogic.controllers.DBAccountingHistoryController;
import databaselogic.controllers.DBBalanceController;
import databaselogic.controllers.DBDetailController;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.ChainUtil;
import domain.Balance;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import projectConstants.CustomConstants;
import services.*;
import views.buttons.AddButton;
import views.buttons.DeleteButton;
import views.dropBoxes.DetailDropBox;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.*;
import utils.RussianMonths;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class MainStage {
    private static final Stage stage = new Stage();
    private Scene scene;
    //layouts
    private BorderPane layout;
    private final BorderPane paneForBalanceTab = new BorderPane();
    private final BorderPane paneForAccoutingESMGTab = new BorderPane();
    private final BorderPane paneForAccoutingESMGMTab = new BorderPane();
    private final BorderPane paneForCostDetail = new BorderPane();
    private TabPane tabPane;
    //tables
    private final BalancesTable balancesTable = new BalancesTable();
    private final SummaryTable summaryTable = new SummaryTable();
    private final CostDetailTable costDetailTable = new CostDetailTable();
    private final ComponentsConsumptionESMGTable esmgTable = new ComponentsConsumptionESMGTable();
    private final ComponentsConsumptionESMGMTable esmgmTable = new ComponentsConsumptionESMGMTable();
    private final RawElectrodeTable rawTable = new RawElectrodeTable();

    //custom classes
    private final DetailDropBox detailDropBox = new DetailDropBox();

    private DBAccountingHistoryController ahController = new DBAccountingHistoryController();
    private DBDetailController detailController = new DBDetailController();
    private DBBalanceController balanceController = new DBBalanceController();

    public MainStage() {
        init();
    }

    private void init() {
        initTabs();
        initLayout();
        initScene();
        initStage();

    }

    private void initStage() {
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(1000);
    }

    private void initScene() {
        scene = new Scene(layout);
    }

    private void initLayout() {
        layout = new BorderPane();
        layout.setCenter(tabPane);
    }

    private void initTabs() {
        tabPane = new TabPane();

        Tab accounting = new Tab("Остатки");
        accounting.setClosable(false);
        Tab costDetailTab = new Tab("Детали");
        costDetailTab.setClosable(false);
        Tab componentsConsumptionForESMG = new Tab("Расход комплектующих для ЕСМГ");
        componentsConsumptionForESMG.setClosable(false);
        Tab componentsConsumptionForESMGM = new Tab("Расход комплектующих для ЕСМГ-М");
        componentsConsumptionForESMGM.setClosable(false);
        Tab electrods = new Tab("Электроды");
        electrods.setClosable(false);

        componentsConsumptionForESMGM.setContent(esmgmTable.getTable());

        addLogicOnSummaryTab(electrods);
        addLogicOnAccoutingESMGMTab(componentsConsumptionForESMGM);
        addLogicOnAccoutingESMGTab(componentsConsumptionForESMG);
        addLogicOnBalanceTab(accounting);
        addLogicOnCostDetailTab(costDetailTab);

        tabPane.getTabs().addAll(
                accounting,
                costDetailTab,
                componentsConsumptionForESMG,
                componentsConsumptionForESMGM,
                electrods
        );
    }

    private void addLogicOnBalanceTab(Tab tab) {
        tab.setContent(paneForBalanceTab);
        paneForBalanceTab.setCenter(balancesTable.getTable());

        HBox horizontal = new HBox(10);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        DetailDropBox balanceDetailDropBox = new DetailDropBox();
        balancesTable.getDetails().forEach(balanceDetailDropBox::deleteDetail);

        Button history = new Button("История");
        Button add = new AddButton().getAdd();

        add.setOnAction(event -> {
            //initRawElectrodeValue detail from drop box
            //TODO: in future delete selected Detail from dropbox
            Detail detail = balanceDetailDropBox.getDetailsBox().getSelectionModel().getSelectedItem();
            if (detail == null)
                return; // TODO: добавить обработку ошибки
            balanceDetailDropBox.deleteDetail(detail);
            // build accounting history for detail
            AccoutingHistoryService.buildSqlForBatchInsertAccHist(detail);
            List<AccoutingHistory> histories = AccoutingHistoryService.getHistoryByDetail(detail);
            // build primitiv for balance
            List<PrimitivityBalance> pBalances = BalanceService.buildPrimitivs(detail);
            // save it on db Balance
            balanceController.saveAll(pBalances);
            // initRawElectrodeValue primitive balance from table (with id)
            pBalances = balanceController.getAllByDetailId(detail.getId());

            List<Balance> balances = ChainUtil.createBalanceChain(
                    Collections.singletonList(detail),
                    pBalances,
                    histories
            );
            if (balances != null) {
                balancesTable.getTable().getItems().addAll(balances);
                detailDropBox.deleteDetail(detail);
            }

        });

        history.setOnAction(event -> {
            //initRawElectrodeValue detail by selected balance
            Balance balance = balancesTable.getTable().getSelectionModel().getSelectedItem();
            if (balance == null)
                return;
            int position = balancesTable.getTable().getItems().indexOf(balance);
            Detail detail = balance.getDetail();
            //initRawElectrodeValue gistory for current detail
            List<AccoutingHistory> ahList = ahController.getByDetail(detail.getId());
            //associated detail with her history
            ChainUtil.associateDetailWithHistory(detail, ahList);
            //convert history for map for AccountingWindow
            Map<RussianMonths, List<AccoutingHistory>> tmp = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
            //send history map in accounting window and wait return result for update (candidates on update)
            tmp = new AccoutingHistoryWindow(tmp).show();
            System.out.println(tmp != null);
            // send candidates for update into updating logic
            if (tmp != null) {
                // upd history by month
                BalanceService.updAccHistoryByDays(balance, tmp);
                // rewrite balance on table
                balancesTable.getTable().getItems().set(position, balance);
                // upd hist on db
                AccoutingHistoryService.buildSqlForBatchUpdAccHist(tmp);
            }
        });

        horizontal.getChildren().addAll(history, balanceDetailDropBox.getDetailsBox(), add);
        paneForBalanceTab.setBottom(horizontal);

    }

    private void addLogicOnCostDetailTab(Tab tab) {
        tab.setContent(paneForCostDetail);
        paneForCostDetail.setCenter(costDetailTable.getCostDetailTable());

        TextField title = new TextField();
        title.setPromptText("Введите название детали");
        TextField count = new TextField();
        count.setPromptText("Введите количество детали");
        TextField cost = new TextField();
        cost.setPromptText("Введите стоимость детали");
        TextField descriptions = new TextField();
        descriptions.setPromptText("Примечание");

        Button add = new AddButton().getAdd();
        Button delete = new DeleteButton().getDelete();

        HBox horizontal = new HBox(15);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);
        horizontal.getChildren().addAll(title, count, cost, descriptions, add, delete);

        paneForCostDetail.setBottom(horizontal);

        add.setOnAction(event -> {
            if (title.getText().isEmpty() || count.getText().isEmpty() || cost.getText().isEmpty()) {
                return;
            }
            Detail d = new Detail(
                    title.getText(),
                    Double.valueOf(count.getText()),
                    new BigDecimal(cost.getText()),
                    descriptions.getText()
            );
            detailController.save(d);
            d = detailController.get(d.getTitle());
            costDetailTable.getCostDetailTable().getItems().add(d);
            detailDropBox.addDetail(d);
            title.clear();
            count.clear();
            cost.clear();
            descriptions.clear();

        });

        delete.setOnAction(event -> {
            if (costDetailTable.getCostDetailTable().getSelectionModel().getSelectedItem() == null)
                return;
            Detail d = costDetailTable.getCostDetailTable().getSelectionModel().getSelectedItem();
            costDetailTable.getCostDetailTable().getItems().remove(d);
            detailController.delete(d.getId());
        });

    }

    private void addLogicOnAccoutingESMGTab(Tab tab) {
        tab.setContent(paneForAccoutingESMGTab);
        paneForAccoutingESMGTab.setCenter(esmgTable.getTable());
        HBox horizontal = new HBox(10);
        DetailDropBox ddb = new DetailDropBox();
        TextField count = new TextField();
        count.setPromptText("количество деталей");
        TextField cost = new TextField();
        cost.setPromptText("стоимость детали");
        Button add = new Button("Добавить");
        Button delete = new Button("Удалить");
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        horizontal.getChildren().addAll(ddb.getDetailsBox(), count, cost, add, delete);
        paneForAccoutingESMGTab.setBottom(horizontal);

        List<Detail> initDet = esmgTable.getTable().getItems();
        initDet.forEach(ddb::deleteDetail);

        add.setOnAction(event -> {
            Detail detail = ddb.getDetailsBox().getSelectionModel().getSelectedItem();

            if (detail == null || count.getText().isEmpty() || cost.getText().isEmpty())
                return;

            Map<Double, BigDecimal> tmp = new HashMap<>();
            tmp.put(Double.valueOf(count.getText()), new BigDecimal(cost.getText()));
            esmgTable.getDetailElectrods().getDetails().put(detail, tmp);
            esmgTable.getTable().getItems().add(detail);
            ddb.deleteDetail(detail);
            // call upd on DB

        });

        delete.setOnAction(event -> {
            Detail detail = esmgTable.getTable().getSelectionModel().getSelectedItem();
            if (detail == null)
                return;
            esmgTable.getTable().getItems().remove(detail);
            esmgTable.getDetailElectrods().getDetails().remove(detail);
            ddb.addDetail(detail);
            // call upd on DB

        });
    }

    private void addLogicOnAccoutingESMGMTab(Tab tab) {
        tab.setContent(paneForAccoutingESMGMTab);
        paneForAccoutingESMGMTab.setCenter(esmgmTable.getTable());
        HBox horizontal = new HBox(10);
        DetailDropBox ddbm = new DetailDropBox();
        TextField count = new TextField();
        count.setPromptText("количество деталей");
        TextField cost = new TextField();
        cost.setPromptText("стоимость детали");
        Button add = new Button("Добавить");
        Button delete = new Button("Удалить");
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        horizontal.getChildren().addAll(ddbm.getDetailsBox(), count, cost, add, delete);
        paneForAccoutingESMGMTab.setBottom(horizontal);
        List<Detail> initDet = esmgTable.getTable().getItems();
        initDet.forEach(ddbm::deleteDetail);

        add.setOnAction(event -> {
            Detail detail = ddbm.getDetailsBox().getSelectionModel().getSelectedItem();

            if (detail == null || count.getText().isEmpty() || cost.getText().isEmpty())
                return;

            Map<Double, BigDecimal> tmp = new HashMap<>();
            tmp.put(Double.valueOf(count.getText()), new BigDecimal(cost.getText()));
            esmgmTable.getDetailElectrods().getDetails().put(detail, tmp);
            esmgmTable.getTable().getItems().add(detail);
            ddbm.deleteDetail(detail);
            // call upd on DB

        });

        delete.setOnAction(event -> {
            Detail detail = esmgmTable.getTable().getSelectionModel().getSelectedItem();
            if (detail == null)
                return;
            esmgmTable.getTable().getItems().remove(detail);
            esmgmTable.getDetailElectrods().getDetails().remove(detail);
            ddbm.addDetail(detail);
            // call upd on DB

        });
    }

    private void addLogicOnSummaryTab(Tab tab) {
        BorderPane pane = new BorderPane();
        BorderPane paneForGridAndRawTable = new BorderPane();
        tab.setContent(pane);

        pane.setPadding(new Insets(10));
        pane.setCenter(summaryTable.getTable());

        Label numberL = new Label("Номер электрода");
        TextField number = new TextField();
        number.setPromptText("№ электрода");

        Label typeL = new Label("Тип электрода");
        ComboBox<String> types = new ComboBox<>();
        types.getItems().addAll(CustomConstants.ESMG, CustomConstants.ESMG_M);

        Label produceDateL = new Label("Дата производства");
        DatePicker produceDate = new DatePicker();
        produceDate.setValue(LocalDate.now());
        produceDate.setShowWeekNumbers(true);

        Label customerL = new Label("Заказчик");
        TextField customer = new TextField();

        Label consumeDateL = new Label("Дата отгрузки");
        DatePicker consumeDate = new DatePicker();
        consumeDate.setValue(LocalDate.now());
        consumeDate.setShowWeekNumbers(true);

        Label noteL = new Label("Примечание");
        TextField note = new TextField();

        TextField nFrom = new TextField();
        nFrom.setPromptText("с № электрода");
        TextField nTo = new TextField();
        nTo.setPromptText("по № электрода");

        TextField rawProduction = new TextField();
        rawProduction.setPromptText("кол-во сырьевых электродов");

        setDateFormat(Arrays.asList(produceDate, consumeDate));

        Button delete = new Button("Удалить");
        Button produce = new Button("Произвести электрод");
        Button bulkProduce = new Button("Массовое производство");
        Button rawProduce = new Button("Сырьевой электрод");

        rawProduce.setOnAction(event -> {
            String count = rawProduction.getText().trim();
            String type = types.getSelectionModel().getSelectedItem();

            if (rawProduction.getText().isEmpty() || type.isEmpty())
                return;// TODO ERROR: добавить обработку ошибки (всплывающее сообщение)
            // хуевая идея передавать список балансов в метод, переделать блять (или нет)
            ObservableList<Balance> updBalance = FXCollections.observableList(
                    CountingService.countingForProduceRawElectrode(type, Integer.valueOf(count), balancesTable.getBalances())
            );
            rawTable.refresh();
            if (!updBalance.isEmpty()) {
                costDetailTable.refresh();
                balancesTable.refresh(updBalance);
            }
            rawProduction.clear();
        });

        bulkProduce.setOnAction(event -> {
            String from = nFrom.getText().trim();
            String to = nTo.getText().trim();
            String type = types.getSelectionModel().getSelectedItem();
            if (from.isEmpty() || to.isEmpty() || type.isEmpty())
                return; // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)

            CountingService.countingForProduceSummaryFromRawElectrode(from, to, type);
            SummaryService.bulkCreateSummaryFromRange(from, to, type, produceDate.getValue(), consumeDate.getValue(), customer.getText(), note.getText());
            summaryTable.refresh();
            rawTable.refresh();
        });

        produce.setOnAction(event -> {
            String elNumber = number.getText().trim();
            String type = types.getSelectionModel().getSelectedItem();
            if (elNumber.isEmpty() || type.isEmpty())
                return; // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)
            CountingService.countingForProduceSummaryFromRawElectrode("0", "1", type);
            Summary summary = new Summary(
                    elNumber,
                    type,
                    produceDate.getValue(),
                    customer.getText(),
                    consumeDate.getValue(),
                    note.getText()
            );
            number.clear();
            customer.clear();
            note.clear();
            SummaryService.save(summary);
            summaryTable.refresh();
            rawTable.refresh();
        });

        delete.setOnAction(event -> {
            Summary summary = summaryTable.getTable().getSelectionModel().getSelectedItem();
            if (summary == null)
                return; // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)
            SummaryService.delete(summary);
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(12);
        gridPane.setPadding(new Insets(10));

        gridPane.add(numberL, 0, 0);
        gridPane.add(number, 1, 0);
        gridPane.add(nFrom, 0, 1);
        gridPane.add(nTo, 1, 1);
        gridPane.add(typeL, 0, 3);
        gridPane.add(types, 1, 3);
        gridPane.add(produceDateL, 0, 5);
        gridPane.add(customerL, 0, 6);
        gridPane.add(consumeDateL, 0, 7);
        gridPane.add(noteL, 0, 8);
        gridPane.add(produceDate, 1, 5);
        gridPane.add(customer, 1, 6);
        gridPane.add(consumeDate, 1, 7);
        gridPane.add(note, 1, 8);
        gridPane.add(rawProduction, 0, 2);
        gridPane.add(rawProduce, 1, 2);
        gridPane.add(produce, 0, 9);
        gridPane.add(bulkProduce, 1, 9);

        pane.setRight(paneForGridAndRawTable);
        paneForGridAndRawTable.setTop(gridPane);
        paneForGridAndRawTable.setCenter(rawTable.getTable());

    }

    private void setDateFormat(List<DatePicker> datePickers) {
        datePickers.forEach(datePicker -> {
            datePicker.setConverter(new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate object) {
                    if (object != null)
                        return CustomConstants.DATE_TIME_FORMATTER.format(object);
                    else return null;
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, CustomConstants.DATE_TIME_FORMATTER);
                    } else {
                        return null;
                    }
                }
            });
        });
    }

    public Stage getStage() {
        return stage;
    }
}
