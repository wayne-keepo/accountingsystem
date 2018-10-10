package views.stages;

import databaselogic.controllers.DBAccountingHistoryController;
import databaselogic.controllers.DBBalanceController;
import databaselogic.controllers.DBDetailController;
import domain.Balance;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import utils.ChainUtil;
import utils.documentGeneration.MyQR;
import utils.documentGeneration.TheBlank;
import utils.enums.RussianMonths;
import utils.enums.Types;
import views.alerts.Alerts;
import views.buttons.AddButton;
import views.buttons.DeleteButton;
import views.dropBoxes.DetailDropBox;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.*;

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
        addLogicOnAccoutingESMG_MTab(componentsConsumptionForESMGM);
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
            if (detail == null){
                Alerts.WARNING_ALERT("Выберите деталь для добавления баланса.");
                return;} // TODO: добавить обработку ошибки
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
            // hz, research, think can speed up?????
            List<Balance> balances = ChainUtil.createBalanceChain(
                    Collections.singletonList(detail),
                    pBalances,
                    histories
            );
            if (balances != null && !balances.isEmpty()) {
                balancesTable.addBalances(balances);
            }

        });

        history.setOnAction(event -> {
            //initRawElectrodeValue detail by selected balance
            Balance balance = balancesTable.getTable().getSelectionModel().getSelectedItem();
            if (balance == null){
                Alerts.WARNING_ALERT("Выберите баланс для просмотра его истории.");
                return;}
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
        Button commit = new Button("Обновить данные");

        HBox horizontal = new HBox(15);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);
        horizontal.getChildren().addAll(title, count, cost, descriptions, add, delete,commit);

        paneForCostDetail.setBottom(horizontal);
        commit.setOnAction(event -> costDetailTable.dataUpdate());
        add.setOnAction(event -> {
            String dTitle = title.getText().trim();
            String dCount = count.getText().trim();
            String dCost = cost.getText().trim();
            if (dTitle.isEmpty() || dCount.isEmpty() || dCost.isEmpty()) {
                Alerts.WARNING_ALERT("Вы не заполнили одно из обязательных полей.");
                return;
            }
            Detail d = new Detail(
                    dTitle,
                    Double.valueOf(dCount),
                    new BigDecimal(dCost),
                    descriptions.getText()
            );
            detailController.save(d);
            d = detailController.get(d.getTitle());
            costDetailTable.addDetail(d);
            detailDropBox.addDetail(d);

            title.clear();
            count.clear();
            cost.clear();
            descriptions.clear();

        });
        delete.setOnAction(event -> {
            if (costDetailTable.getCostDetailTable().getSelectionModel().getSelectedItem() == null) {
                Alerts.WARNING_ALERT("Выберите деталь для удаления.");
                return;
            }
            Detail d = costDetailTable.getCostDetailTable().getSelectionModel().getSelectedItem();
            costDetailTable.removeDetail(d);
            detailController.delete(d.getId());
        });
    }
    // надо переделать но не хочу возиться сейчас
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
        Button commit = new Button("Обновить данные");

        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        horizontal.getChildren().addAll(ddb.getDetailsBox(), count, cost, add, delete,commit);
        paneForAccoutingESMGTab.setBottom(horizontal);

        List<Detail> initDet = esmgTable.getTable().getItems();
        initDet.forEach(ddb::deleteDetail);

        commit.setOnAction(event -> esmgTable.dataUpdate());
        add.setOnAction(event -> {
            Detail detail = ddb.getDetailsBox().getSelectionModel().getSelectedItem();
            Double newCount = Double.valueOf(count.getText().trim()) ;
            BigDecimal newCost = new BigDecimal(cost.getText().trim());
            if (detail == null)
                return;
            DetailElectrodePrimitive primitive = DetailElectrodeService.add(detail,newCount,newCost,Types.ESMG.eng());
            Map<Double, BigDecimal> tmp = new HashMap<>();
            tmp.put(newCount,newCost);
            esmgTable.getDetailElectrods().getDetails().put(detail, tmp);
            esmgTable.getDetailElectrods().getIds().add(primitive.getId());
            esmgTable.getTable().getItems().add(detail);
            ddb.deleteDetail(detail);

        });

        delete.setOnAction(event -> {
            Detail detail = esmgTable.getTable().getSelectionModel().getSelectedItem();
            if (detail == null)
                return;
            esmgTable.getTable().getItems().remove(detail);
            esmgTable.getDetailElectrods().getDetails().remove(detail);
            ddb.addDetail(detail);
            DetailElectrodeService.deleteByDetailAndElType(detail.getId(), Types.ESMG.eng());

        });
    }
    // надо переделать но не хочу возиться сейчас
    private void addLogicOnAccoutingESMG_MTab(Tab tab) {
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
        Button commit = new Button("Обновить данные");

        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        horizontal.getChildren().addAll(ddbm.getDetailsBox(), count, cost, add, delete,commit);
        paneForAccoutingESMGMTab.setBottom(horizontal);
        List<Detail> initDet = esmgTable.getTable().getItems();
        initDet.forEach(ddbm::deleteDetail);

        commit.setOnAction(event -> esmgmTable.dataUpdate());
        add.setOnAction(event -> {
            Detail detail = ddbm.getDetailsBox().getSelectionModel().getSelectedItem();
            Double newCount = Double.valueOf(count.getText().trim()) ;
            BigDecimal newCost = new BigDecimal(cost.getText().trim());
            if (detail == null)
                return;
            DetailElectrodePrimitive primitive = DetailElectrodeService.add(detail,newCount,newCost,Types.ESMG_M.eng());
            Map<Double, BigDecimal> tmp = new HashMap<>();
            tmp.put(newCount,newCost);
            esmgTable.getDetailElectrods().getDetails().put(detail, tmp);
            esmgTable.getDetailElectrods().getIds().add(primitive.getId());
            esmgTable.getTable().getItems().add(detail);
            ddbm.deleteDetail(detail);

        });

        delete.setOnAction(event -> {
            Detail detail = esmgmTable.getTable().getSelectionModel().getSelectedItem();
            if (detail == null)
                return;
            esmgmTable.getTable().getItems().remove(detail);
            esmgmTable.getDetailElectrods().getDetails().remove(detail);
            ddbm.addDetail(detail);
            DetailElectrodeService.deleteByDetailAndElType(detail.getId(), Types.ESMG_M.eng());

        });
    }

    private void addLogicOnSummaryTab(Tab tab) {
        BorderPane pane = new BorderPane();
        BorderPane paneForGridAndRawTable = new BorderPane();
        tab.setContent(pane);

        pane.setPadding(new Insets(10));
        pane.setCenter(summaryTable.getTable());

        Label rawTitle = new Label("Производство сырых электродов");
        Label elecTitle = new Label("Продажа электродов");
        Label docTitle = new Label("Формирование документа");

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

        Label positionL = new Label("Должность");
        TextField position = new TextField();
        position.setPromptText("должность");
        Label fioL = new Label("ФИО");
        TextField fio = new TextField("");
        fio.setPromptText("ФИО через пробел");
        Label cableLengthL = new Label("Длина кабеля");
        TextField cableLength = new TextField("");
        cableLength.setPromptText("длина кабеля");
        Label docDateL = new Label("Дата");
        DatePicker docDate = new DatePicker();
        docDate.setValue(LocalDate.now());
        docDate.setShowWeekNumbers(true);
        setDateFormat(Arrays.asList(produceDate, consumeDate,docDate));

        Button delete = new Button("Удалить");
        Button bulkProduce = new Button("Произвести электрод");
        Button rawProduce = new Button("Сырьевой электрод");

        rawProduce.setOnAction(event -> {
            String count = rawProduction.getText().trim();
            String type = types.getSelectionModel().getSelectedItem();

            if (count.isEmpty() || type.isEmpty()){
                Alerts.WARNING_ALERT("Не заполнено обязательное поле или не выбран тип электрода.");
                return;}
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
            String empPosition = position.getText().trim();
            String cabLen = cableLength.getText().trim();
            String empFio = fio.getText().trim();
            String doc = docDate.getValue().toString();

            if (type.isEmpty()||(from.isEmpty() && to.isEmpty())){
                Alerts.WARNING_ALERT("Выберите тип электрода и введите количество");
                return;
            }
            if (!from.isEmpty() && to.isEmpty()){
                CountingService.countingForProduceSummaryFromRawElectrode("0", "1", type);
                Summary summary = new Summary(from, type, produceDate.getValue(), customer.getText().trim(), consumeDate.getValue(), note.getText().trim());
                SummaryService.save(summary);
                if (!empPosition.isEmpty() && !cabLen.isEmpty() && !empFio.isEmpty() && !doc.isEmpty()) {
                    new TheBlank().theDoc(from, "", cabLen, empPosition, empFio, doc);
                    new MyQR().theQR(from);
                }
            } else {
                CountingService.countingForProduceSummaryFromRawElectrode(from, to, type);
                SummaryService.bulkCreateSummaryFromRange(from, to, type, produceDate.getValue(), consumeDate.getValue(), customer.getText(), note.getText());
                if (!empPosition.isEmpty() && !cabLen.isEmpty() && !empFio.isEmpty() && !doc.isEmpty()) {
                    new TheBlank().theDoc(from, to, cabLen, empPosition, empFio, doc);
                    new MyQR().theQR(from+to);
                }

            }
            customer.clear();
            note.clear();

            summaryTable.refresh();
            rawTable.refresh();
        });

        delete.setOnAction(event -> {
            Summary summary = summaryTable.getTable().getSelectionModel().getSelectedItem();
            if (summary == null){
                Alerts.WARNING_ALERT("Выберите элемент для удаления.");
                return; } // TODO ERROR: добавить обработку ошибки (всплывающее сообщение)
            SummaryService.delete(summary);
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(12);
        gridPane.setPadding(new Insets(10));
        //raw el
        gridPane.add(rawTitle, 0, 0,2,1);
        gridPane.add(typeL, 0, 2);
        gridPane.add(types, 1, 2);
        gridPane.add(rawProduction,0,3);
        gridPane.add(rawProduce, 1, 3);
        //el
        gridPane.add(elecTitle, 0, 4,2,1);
        gridPane.add(nFrom, 0, 5);
        gridPane.add(nTo, 1, 5);
        gridPane.add(produceDateL, 0, 6);
        gridPane.add(produceDate, 1, 6);
        gridPane.add(customerL, 0, 7);
        gridPane.add(customer, 1, 7);
        gridPane.add(consumeDateL, 0, 8);
        gridPane.add(consumeDate, 1, 8);
        gridPane.add(noteL, 0, 9);
        gridPane.add(note, 1, 9);
        //doc
        gridPane.add(docTitle,0,11,2,1);
        gridPane.add(cableLengthL,0,12);
        gridPane.add(cableLength,1,12);
        gridPane.add(positionL,0,13);
        gridPane.add(position,1,13);
        gridPane.add(fioL,0,14);
        gridPane.add(fio,1,14);
        gridPane.add(docDateL,0,15);
        gridPane.add(docDate,1,15);
        gridPane.add(bulkProduce, 1, 16,2,1);

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
