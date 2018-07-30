package views.stages;

import databaselogic.controllers.DBAccountingHistoryController;
import databaselogic.controllers.DBBalanceController;
import databaselogic.controllers.DBDetailController;
import databaselogic.controllers.DBElectrodeController;
import domain.ElectrodeSummary;
import utils.ChainUtil;
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
import services.*;
import views.buttons.AddButton;
import views.buttons.CommitButton;
import views.buttons.DeleteButton;
import views.dropBoxes.DetailDropBox;
import views.modalWindows.AccoutingHistoryWindow;
import views.tables.*;
import utils.RussianMonths;

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
        accounting.setClosable(false);
        Tab componentsConsumptionForESMG = new Tab("Расход комплектующих для ЕСМГ");
        componentsConsumptionForESMG.setClosable(false);
        Tab componentsConsumptionForESMGM = new Tab("Расход комплектующих для ЕСМГ-М");
        componentsConsumptionForESMGM.setClosable(false);
        Tab electrods = new Tab("Электроды");
        electrods.setClosable(false);
        Tab costDetailTab = new Tab("Детали");
        costDetailTab.setClosable(false);
        Tab product = new Tab("Создать эклектрод");
        product.setClosable(false);

        componentsConsumptionForESMG.setContent(esmgTable.getTable());

        componentsConsumptionForESMGM.setContent(esmgmTable.getTable());

        electrods.setContent(electrodsTable.getTable());
        addLogicOnBalanceTab(accounting);
        addLogicOnCostDetailTab(costDetailTab);
        addLogicOnCreateElectrodeTab(product);

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
// think can delete it , hmmm ???
//        commit.setOnAction(event -> {
//            Balance balance = balancesTable.getTable().getSelectionModel().getSelectedItem();
//            BalanceService.updateBalance(balance);
//        });

        add.setOnAction(event -> {
            //get detail from drop box
            //TODO: in future delete selected Detail from dropbox
            Detail detail = detailDropBox.getDetailsBox().getSelectionModel().getSelectedItem();
            if (detail == null)
                return;
            // build accounting history for detail
            AccoutingHistoryService.buildSqlForBatchInsertAccHist(detail);
            List<AccoutingHistory> histories = AccoutingHistoryService.getHistoryByDetail(detail);
            // build primitiv for balance
            List<PrimitivityBalance> pBalances = BalanceService.buildPrimitivs(detail);
            // save it on db Balance
            balanceController.saveAll(pBalances);
            // get primitive balance from table (with id)
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
            //get detail by selected balance
            Balance balance = balancesTable.getTable().getSelectionModel().getSelectedItem();
            if (balance == null)
                return;
            int position = balancesTable.getTable().getItems().indexOf(balance);
            Detail detail = balance.getDetail();
            //get gistory for current detail
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

        horizontal.getChildren().addAll(history, detailDropBox.getDetailsBox(), add, commit);
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
        Button commit = new CommitButton().getCommit();

        HBox horizontal = new HBox(15);
        horizontal.setPadding(new Insets(10, 0, 10, 0));
        horizontal.setAlignment(Pos.BOTTOM_CENTER);
        horizontal.getChildren().addAll(title, count, cost, descriptions, add, delete, commit);

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

        commit.setOnAction(event -> {
            if (costDetailTable.getChanges().isEmpty())
                return;
            DetailService.findCandidatesOnUpdating(costDetailTable.getCostDetailTable().getItems(), costDetailTable.getChanges());
            costDetailTable.clearChanges();
        });

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
        Label range = new Label("Введите в поля ниже с какого по какой номер создать электрод");

        TextField number = new TextField();
        TextField customer = new TextField();
        TextField note = new TextField();
        TextField nFrom = new TextField();
        nFrom.setPromptText("с № электрода");
        TextField nTo = new TextField();
        nTo.setPromptText("по № электрода");

        CheckBox isBulkCreate = new CheckBox("С созданием истории для эклектродов");
        isBulkCreate.setSelected(false);

        DatePicker produceDate = new DatePicker();
        produceDate.setValue(LocalDate.now());
        produceDate.setShowWeekNumbers(true);

        DatePicker consumeDate = new DatePicker();
        consumeDate.setValue(LocalDate.now());
        consumeDate.setShowWeekNumbers(true);

        setDateFormat(Arrays.asList(produceDate, consumeDate));

        ComboBox<String> types = new ComboBox<>();
        types.getItems().addAll(CustomConstants.ESMG, CustomConstants.ESMG_M);

        Button add = new AddButton().getAdd();
        Button delete = new DeleteButton().getDelete();
        Button produce = new Button("Произвести электрод");

        Button bulkProduce = new Button("Массовое производство");

        bulkProduce.setOnAction(event -> {
            if (nFrom.getText().isEmpty() && nTo.getText().isEmpty())
                return;
            if (isBulkCreate.isSelected()) {
                List<ElectrodeSummary> es = SummaryService.bulkCreateElectrodeSummaryFromRange(
                        nFrom.getText(), nTo.getText(), types.getSelectionModel().getSelectedItem(),
                        produceDate.getValue(), consumeDate.getValue(), customer.getText(), note.getText());
                electrodsTable.getTable().getItems().addAll(es);
            } else {
                List<Electrod> electrods = ElectrodeService.bulkCreateElectrodeFromRange(nFrom.getText(), nTo.getText(), types.getSelectionModel().getSelectedItem());
                createElectrodeTable.getTable().getItems().addAll(electrods);
            }
        });
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

        GridPane.setConstraints(range, 0, 9, 2, 1);
        GridPane.setConstraints(nFrom,0,10);
        GridPane.setConstraints(nTo,1,10);
        GridPane.setConstraints(bulkProduce,0,11);
        GridPane.setConstraints(isBulkCreate,1,11);

        gridPane.getChildren().addAll(
                numberL, typeL, number, types, add, delete, produce,
                produceDate, customerL, consumeDate, noteL, produceDateL, customer, consumeDateL, note,range,nFrom,nTo,bulkProduce,isBulkCreate);
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
