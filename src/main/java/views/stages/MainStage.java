package views.stages;

import databaselogic.controllers.DBAccountingHistoryController;
import databaselogic.controllers.DBBalanceController;
import databaselogic.controllers.DBDetailController;
import databaselogic.controllers.DBElectrodeController;
import databaselogic.utils.ChainUtil;
import domain.Balance;
import domain.Electrod;
import entities.AccoutingHistory;
import entities.Detail;

import entities.PrimitivityBalance;
import entities.Summary;
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
import services.AccoutingHistoryService;
import services.BalanceService;
import services.ElectrodeService;
import services.SummaryService;
import views.buttons.AddButton;
import views.buttons.CommitButton;
import views.buttons.DeleteButton;
import views.dropBoxes.DetailDropBox;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.*;
import views.tables.utils.RussianMonths;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainStage {
    private static final Stage stage = new Stage();
    private Scene scene;
    //layouts
    private BorderPane layout;
    private final BorderPane paneForBalanceTab = new BorderPane();
    private final BorderPane paneForAccoutingESMGTab = new BorderPane();
    private final BorderPane paneForAccoutingESMGMTab = new BorderPane();
    private final BorderPane paneForElectrodeTab = new BorderPane();
    private final BorderPane paneForCostDetail = new BorderPane();
    private final BorderPane paneForCreateElectrodeTab = new BorderPane();
    private TabPane tabPane;
    //tables
    //private TableView<Detail> table;
    private final BalancesTable balancesTable = new BalancesTable();
    private final ElectrodsTable electrodsTable = new ElectrodsTable();
    private final CostDetailTable costDetailTable = new CostDetailTable();
    private final ComponentsConsumptionESMGTable esmgTable = new ComponentsConsumptionESMGTable();
    private final ComponentsConsumptionESMGMTable esmgmTable = new ComponentsConsumptionESMGMTable();
    private final CreateElectrodeTable createElectrodeTable = new CreateElectrodeTable();

    private final DetailDropBox detailDropBox = new DetailDropBox();

    private DBAccountingHistoryController ahController = new DBAccountingHistoryController();
    private DBElectrodeController electrodeController = new DBElectrodeController();
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
        Tab componentsConsumptionForESMG = new Tab("Расход комплектующих для ЕСМГ");
        Tab componentsConsumptionForESMGM = new Tab("Расход комплектующих для ЕСМГ-М");
        Tab electrods = new Tab("Электроды");
        Tab costDetailTab = new Tab("Детали");
        Tab product = new Tab("Создать эклектрод");

        //remove
//        product.setContent(treeView.getTree());
        //set table here
//        accounting.setContent(paneForBalanceTab);
//        paneForBalanceTab.setCenter(balancesTable.getTable());

        componentsConsumptionForESMG.setContent(esmgTable.getTable());

        componentsConsumptionForESMGM.setContent(esmgmTable.getTable());

        electrods.setContent(electrodsTable.getTable());
        addLogicOnBalanceTab(accounting);
        addLogicOnCostDetailTab(costDetailTab);
        addLogicOnCreateElectrodeTab(product);

//        costDetailTab.setContent(costDetailTable.getCostDetailTable());


//        detailDropBox.getDetailsBox().setMaxSize(170,20);
//
//        VBox vertical = new VBox(20);
//        vertical.setPadding(new Insets(10,5,5,5));
//        vertical.setAlignment(Pos.CENTER_RIGHT);
//        Button add = new AddButton().getAdd();
//        add.setOnAction(event -> {
//            String d = detailDropBox.getDetailsBox().getValue();
//            Balance testBalance = new Balance();
//            testBalance.setDetail(
//                    InitializerForTest.getDetails().stream().findFirst(d::equals());
//            );
//            balancesTable.getTable().getItems().add(testBalance);
//        });
//        vertical.getChildren().addAll(detailDropBox.getDetailsBox(), add);
//        paneForBalanceTab.setRight(vertical);

        tabPane.getTabs().addAll(
                accounting,
                componentsConsumptionForESMG,
                componentsConsumptionForESMGM,
                electrods,
                costDetailTab,
                product
        );
    }

    private void addLogicOnBalanceTab(Tab tab) {
        tab.setContent(paneForBalanceTab);
        paneForBalanceTab.setCenter(balancesTable.getTable());

        HBox horizontal = new HBox(10);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);

        Button history = new Button("История");
        Button add = new AddButton().getAdd();
        Button commit = new CommitButton().getCommit();

        commit.setOnAction(event -> {
            Balance balance = balancesTable.getTable().getSelectionModel().getSelectedItem();
            BalanceService.updateBalance(balance);
        });
        add.setOnAction(event -> {
            //get detail from drop box
            Detail detail = detailDropBox.getDetailsBox().getSelectionModel().getSelectedItem();
            // build primitiv for balance
            List<PrimitivityBalance> pBalances = BalanceService.buildPrimitivs(detail);
            // ya ne pomnu sho tut dalshe (
            balanceController.saveAll(pBalances);
            pBalances = balanceController.getAllByDetailId(detail.getId());
            List<Balance> balances = ChainUtil.createBalanceChain(
                    Collections.singletonList(detail),
                    pBalances
            );
            balancesTable.getTable().getItems().addAll(balances);
            // build accounting history for detail
            AccoutingHistoryService.buildSqlForBatchInsertAccHist(detail);
        });

        history.setOnAction(event -> {
            //get detail by selected balance
            Detail detail = balancesTable.getTable().getSelectionModel().getSelectedItem().getDetail();
            //get gistory for current detail
            List<AccoutingHistory> ahList = ahController.getByDetail(detail.getId());
            //associated detail with her history
            ChainUtil.associateDetailWithHistory(detail,ahList);
            //convert history for map for AccountingWindow
            Map<RussianMonths,List<AccoutingHistory>> tmp = AccoutingHistoryService.historyToMapForAccoutingWindow(ahList);
            //send history map in accounting window and wait return result for update (candidates on update)
            tmp  = new AccoutingHistoryWindow(tmp).show();
            // send candidates for update into updating logic
            if (tmp!=null)
                AccoutingHistoryService.buildSqlForBatchUpdAccHist(tmp);
        });

        horizontal.getChildren().addAll(history,detailDropBox.getDetailsBox(), add, commit);
        paneForBalanceTab.setBottom(horizontal);

    }

    private void addLogicOnCostDetailTab(Tab tab) {
//        detailController = new DBDetailController();
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
        Button commit = new CommitButton().getCommit();

        HBox horizontal = new HBox(15);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);
        horizontal.getChildren().addAll(title, count, cost, descriptions, add, delete, commit);

        paneForCostDetail.setBottom(horizontal);

        add.setOnAction(event -> {
            Detail d = new Detail(
                    title.getText(),
                    Double.valueOf(count.getText()),
                    new BigDecimal(cost.getText()),
                    descriptions.getText()
            );
            detailController.save(d);
            title.clear();
            count.clear();
            cost.clear();
            descriptions.clear();
            costDetailTable.getCostDetailTable().getItems().add(d);
        });
//
        delete.setOnAction(event -> {
            TableView tmp = costDetailTable.getCostDetailTable();
            Detail d = (Detail) tmp.getSelectionModel().getSelectedItem();
            detailController.delete(d.getId());
            tmp.getItems().remove(d);
        });
//        commit.setOnAction(event -> {
//            List<Detail> det = costDetailTable.getCostDetailTable().getItems().stream().filter(d->d.getId()==0).collect(Collectors.toList());
//            System.out.println(det.toString());
//        });

    }

    private void addLogicOnCreateElectrodeTab(Tab tab) {
        tab.setContent(paneForCreateElectrodeTab);

        paneForCreateElectrodeTab.setPadding(new Insets(10));
        paneForCreateElectrodeTab.setCenter(createElectrodeTable.getTable());

        Label numberL = new Label("Номер электрода");
        Label typeL = new Label("Тип электрода");
        Label produceDateL = new Label("Дата производства");
        Label customerL = new Label("Заказчик");
        Label consumeDateL = new Label("Дата отгрузки");
        Label noteL = new Label("Примечание");

        TextField number = new TextField();

        DatePicker produceDate = new DatePicker();
        produceDate.setValue(LocalDate.now());
        produceDate.setShowWeekNumbers(true);

        DatePicker consumeDate = new DatePicker();
        consumeDate.setValue(LocalDate.now());
        consumeDate.setShowWeekNumbers(true);

        setDateFormat(Arrays.asList(produceDate, consumeDate));

        TextField customer = new TextField();
        TextField note = new TextField();

        ComboBox<String> types = new ComboBox<>();
        types.getItems().addAll(CustomConstants.ESMG, CustomConstants.ESMG_M);

        Button add = new AddButton().getAdd();
        Button delete = new DeleteButton().getDelete();
        Button produce = new Button("Произвести электрод");
//Integer idSummary, Integer idElectrode, LocalDate produceDate, String customer, LocalDate consumeDate, String note
        produce.setOnAction(event -> {
            Summary summary = new Summary();
            Electrod electrod = createElectrodeTable.getTable().getSelectionModel().getSelectedItem();
            summary.setIdElectrode(electrod.getId());
            summary.setProduceDate(produceDate.getValue());
            summary.setCustomer(customer.getText());
            summary.setConsumeDate(consumeDate.getValue());
            summary.setNote(note.getText());
            customer.clear();
            note.clear();
            SummaryService.save(summary);
            electrodsTable.refresh();
        });

        add.setOnAction(event -> {
            Electrod electrod = new Electrod();
            electrod.setElectrodNumber(number.getText());
            electrod.setType(types.getSelectionModel().getSelectedItem());
            ElectrodeService.save(electrod);
            electrod = ElectrodeService.getByNumber(electrod.getElectrodNumber());
            createElectrodeTable.getTable().getItems().addAll(electrod);
            number.clear();
            types.getSelectionModel().clearSelection();
        });
        delete.setOnAction(event -> {
            Electrod electrod = createElectrodeTable.getTable().getSelectionModel().getSelectedItem();
            ElectrodeService.delete(electrod);
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(12);
        gridPane.setPadding(new Insets(10));

        GridPane.setConstraints(numberL, 0, 0);
        GridPane.setConstraints(typeL, 0, 1);
        GridPane.setConstraints(number, 1, 0);
        GridPane.setConstraints(types, 1, 1);

        GridPane.setConstraints(produceDateL, 0, 4);
        GridPane.setConstraints(customerL, 0, 5);
        GridPane.setConstraints(consumeDateL, 0, 6);
        GridPane.setConstraints(noteL, 0, 7);
        GridPane.setConstraints(produceDate, 1, 4);
        GridPane.setConstraints(customer, 1, 5);
        GridPane.setConstraints(consumeDate, 1, 6);
        GridPane.setConstraints(note, 1, 7);

        GridPane.setConstraints(add, 0, 2);
        GridPane.setConstraints(delete, 1, 2);
        GridPane.setConstraints(produce, 0, 8, 2, 1);

        gridPane.getChildren().addAll(
                numberL, typeL, number, types, add, delete, produce,
                produceDate, customerL, consumeDate, noteL, produceDateL, customer, consumeDateL, note);
        paneForCreateElectrodeTab.setRight(gridPane);

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
