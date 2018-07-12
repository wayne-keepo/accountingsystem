package views.modalWindows;

import entities.AccoutingHistory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.dropBoxes.MonthBox;
import views.tables.utils.RussianMonths;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class AccoutingHistoryWindow extends Application{
    private  BorderPane mainPane = null;
    private  HBox hBoxCalendar = null;
    private  HBox hBoxButtons = null;
    private  GridPane gridPane = null;
    private  Map<RussianMonths,List<AccoutingHistory>> historyMap = null;

    private  MonthBox monthBox = null;
    private  Map<Map<Integer,Label>,List<TextField>> matrix = null;

    public void show(Map<RussianMonths,List<AccoutingHistory>> ah) {
        historyMap = ah;
        settingLayouts();
        initCalendar();
//        initLablesAndFields();
        initButtons();
//        logic();
        Stage window = new Stage();
        Scene scene = new Scene(mainPane, 350, 590);
        window.setTitle("Приход/Расход по дням");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.showAndWait();
    }

    private void settingLayouts(){
        mainPane = new BorderPane();
        hBoxCalendar = new HBox();
        hBoxButtons = new HBox();
        gridPane = new GridPane();

        hBoxCalendar.setAlignment(Pos.TOP_CENTER);
        hBoxCalendar.setPadding(new Insets(5));
        hBoxButtons.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxButtons.setPadding(new Insets(5));
        hBoxButtons.setSpacing(20.0);

        ColumnConstraints column0 = new ColumnConstraints(30);
        ColumnConstraints column3 = new ColumnConstraints(30);
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column4 = new ColumnConstraints(50);
        ColumnConstraints column5 = new ColumnConstraints(50);

        gridPane.getColumnConstraints().addAll(column0,column1,column2,column3,column4,column5);
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        mainPane.setTop(hBoxCalendar);
        mainPane.setCenter(gridPane);
        mainPane.setBottom(hBoxButtons);
    }

    private void initButtons(){
        Button save = new Button("Сохранить");
        Button close = new Button("Закрыть");
        hBoxButtons.getChildren().addAll(save,close);
    }

    // tear out your eye and cut off my hands, fuck :((
    private void initLablesAndFields(List<AccoutingHistory> history){
        Label l1	= new Label("1");		TextField txf1i		= new TextField("1i");		TextField txf1o		= new TextField("1o");
        Label l2	= new Label("2");		TextField txf2i		= new TextField("2i");		TextField txf2o		= new TextField("2o");
        Label l3	= new Label("3");		TextField txf3i		= new TextField("3i");		TextField txf3o		= new TextField("3o");
        Label l4	= new Label("4");		TextField txf4i		= new TextField("4i");		TextField txf4o		= new TextField("4o");
        Label l5	= new Label("5");		TextField txf5i		= new TextField("5i");		TextField txf5o		= new TextField("5o");
        Label l6	= new Label("6");		TextField txf6i		= new TextField("6i");		TextField txf6o		= new TextField("6o");
        Label l7	= new Label("7");		TextField txf7i		= new TextField("7i");		TextField txf7o		= new TextField("7o");
        Label l8	= new Label("8");		TextField txf8i		= new TextField("8i");		TextField txf8o		= new TextField("8o");
        Label l9	= new Label("9");		TextField txf9i		= new TextField("9i");		TextField txf9o		= new TextField("9o");
        Label l11	= new Label("10");		TextField txf11i	= new TextField("10i");		TextField txf11o	= new TextField("10o");
        Label l12	= new Label("11");		TextField txf12i	= new TextField("11i");		TextField txf12o	= new TextField("11o");
        Label l13	= new Label("12");		TextField txf13i	= new TextField("12i");		TextField txf13o	= new TextField("12o");
        Label l14	= new Label("13");		TextField txf14i	= new TextField("13i");		TextField txf14o	= new TextField("13o");
        Label l15	= new Label("14");		TextField txf15i	= new TextField("14i");		TextField txf15o	= new TextField("14o");
        Label l16	= new Label("15");		TextField txf16i	= new TextField("15i");		TextField txf16o	= new TextField("15o");
        Label l17	= new Label("16");		TextField txf17i	= new TextField("16i");		TextField txf17o	= new TextField("16o");
        Label l18	= new Label("17");		TextField txf18i	= new TextField("17i");		TextField txf18o	= new TextField("17o");
        Label l19	= new Label("18");		TextField txf19i	= new TextField("18i");		TextField txf19o	= new TextField("18o");
        Label l20	= new Label("19");		TextField txf20i	= new TextField("19i");		TextField txf20o	= new TextField("19o");
        Label l21	= new Label("20");		TextField txf21i	= new TextField("20i");		TextField txf21o	= new TextField("20o");
        Label l22	= new Label("21");		TextField txf22i	= new TextField("21i");		TextField txf22o	= new TextField("21o");
        Label l23	= new Label("22");		TextField txf23i	= new TextField("22i");		TextField txf23o	= new TextField("22o");
        Label l24	= new Label("23");		TextField txf24i	= new TextField("23i");		TextField txf24o	= new TextField("23o");
        Label l25	= new Label("24");		TextField txf25i	= new TextField("24i");		TextField txf25o	= new TextField("24o");
        Label l26	= new Label("25");		TextField txf26i	= new TextField("25i");		TextField txf26o	= new TextField("25o");
        Label l27	= new Label("26");		TextField txf27i	= new TextField("26i");		TextField txf27o	= new TextField("26o");
        Label l28	= new Label("27");		TextField txf28i	= new TextField("27i");		TextField txf28o	= new TextField("27o");
        Label l29	= new Label("28");		TextField txf29i	= new TextField("28i");		TextField txf29o	= new TextField("28o");
        Label l30	= new Label("29");		TextField txf30i	= new TextField("29i");		TextField txf30o	= new TextField("29o");
        Label l31	= new Label("30");		TextField txf31i	= new TextField("30i");		TextField txf31o	= new TextField("30o");
        Label l32	= new Label("31");		TextField txf32i	= new TextField("31i");		TextField txf32o	= new TextField("31o");

        List<Label> lables
                = Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9,l11,l12,l13,l14,l15,l16,l17,l18,l19,l20,l21,l22,l23,l24,l25,l26,l27,l28,l29,l30,l31,l32);
        List<TextField> fieldsI
                = Arrays.asList(txf1i,txf2i,txf3i,txf4i,txf5i,txf6i,txf7i,txf8i,txf9i,txf11i,txf12i,txf13i,txf14i,txf15i,txf16i,txf17i,txf18i,txf19i,txf20i,txf21i,txf22i,txf23i,txf24i,txf25i,txf26i,txf27i,txf28i,txf29i,txf30i,txf31i,txf32i);
        List<TextField> fieldsO
                = Arrays.asList(txf1o,txf2o,txf3o,txf4o,txf5o,txf6o,txf7o,txf8o,txf9o,txf11o,txf12o,txf13o,txf14o,txf15o,txf16o,txf17o,txf18o,txf19o,txf20o,txf21o,txf22o,txf23o,txf24o,txf25o,txf26o,txf27o,txf28o,txf29o,txf30o,txf31o,txf32o);

        matrix = new LinkedHashMap<>();
        for (Integer i = 0; i < lables.size(); i++) {
            Map<Integer, Label> some = new LinkedHashMap<>();
            some.put(i + 1, lables.get(i));
            matrix.put(some, Arrays.asList(fieldsI.get(i), fieldsO.get(i)));
        }

        fillGrid();
//        gridPane.add(l1,0,1);	gridPane.add(txf1i,1,1);	gridPane.add(txf1o,2,1);
//        gridPane.add(l2,0,2);	gridPane.add(txf2i,1,2);	gridPane.add(txf2o,2,2);
//        gridPane.add(l3,0,3);	gridPane.add(txf3i,1,3);	gridPane.add(txf3o,2,3);
//        gridPane.add(l4,0,4);	gridPane.add(txf4i,1,4);	gridPane.add(txf4o,2,4);
//        gridPane.add(l5,0,5);	gridPane.add(txf5i,1,5);	gridPane.add(txf5o,2,5);
//        gridPane.add(l6,0,6);	gridPane.add(txf6i,1,6);	gridPane.add(txf6o,2,6);
//        gridPane.add(l7,0,7);	gridPane.add(txf7i,1,7);	gridPane.add(txf7o,2,7);
//        gridPane.add(l8,0,8);	gridPane.add(txf8i,1,8);	gridPane.add(txf8o,2,8);
//        gridPane.add(l9,0,9);	gridPane.add(txf9i,1,9);	gridPane.add(txf9o,2,9);
//
//        gridPane.add(l11,0,10); gridPane.add(txf11i,1,10);	gridPane.add(txf11o,2,10);
//        gridPane.add(l12,0,11); gridPane.add(txf12i,1,11);	gridPane.add(txf12o,2,11);
//        gridPane.add(l13,0,12); gridPane.add(txf13i,1,12);	gridPane.add(txf13o,2,12);
//        gridPane.add(l14,0,13); gridPane.add(txf14i,1,13);	gridPane.add(txf14o,2,13);
//        gridPane.add(l15,0,14); gridPane.add(txf15i,1,14);	gridPane.add(txf15o,2,14);
//        gridPane.add(l16,0,15); gridPane.add(txf16i,1,15);	gridPane.add(txf16o,2,15);
//        gridPane.add(l17,0,16); gridPane.add(txf17i,1,16);	gridPane.add(txf17o,2,16);
//
//        gridPane.add(l18,3,1);	gridPane.add(txf18i,4,1);	gridPane.add(txf18o,5,1);
//        gridPane.add(l19,3,2);	gridPane.add(txf19i,4,2);	gridPane.add(txf19o,5,2);
//        gridPane.add(l20,3,3);	gridPane.add(txf20i,4,3);	gridPane.add(txf20o,5,3);
//        gridPane.add(l21,3,4);	gridPane.add(txf21i,4,4);	gridPane.add(txf21o,5,4);
//        gridPane.add(l22,3,5);	gridPane.add(txf22i,4,5);	gridPane.add(txf22o,5,5);
//        gridPane.add(l23,3,6);	gridPane.add(txf23i,4,6);	gridPane.add(txf23o,5,6);
//        gridPane.add(l24,3,7);	gridPane.add(txf24i,4,7);	gridPane.add(txf24o,5,7);
//        gridPane.add(l25,3,8);	gridPane.add(txf25i,4,8);	gridPane.add(txf25o,5,8);
//        gridPane.add(l26,3,9);	gridPane.add(txf26i,4,9);	gridPane.add(txf26o,5,9);
//
//        gridPane.add(l27,3,10); gridPane.add(txf27i,4,10);	gridPane.add(txf27o,5,10);
//        gridPane.add(l28,3,11); gridPane.add(txf28i,4,11);	gridPane.add(txf28o,5,11);
//        gridPane.add(l29,3,12); gridPane.add(txf29i,4,12);	gridPane.add(txf29o,5,12);
//        gridPane.add(l30,3,13); gridPane.add(txf30i,4,13);	gridPane.add(txf30o,5,13);
//        gridPane.add(l31,3,14); gridPane.add(txf31i,4,14);	gridPane.add(txf31o,5,14);
//        gridPane.add(l32,3,15); gridPane.add(txf32i,4,15);	gridPane.add(txf32o,5,15);
    }
    private void associatedDayWithField(){

    }
    private void fillGrid(){
        Label incoming	= new Label("Приход");      Label inc	= new Label("Приход");
        Label outcoming	= new Label("Расход");      Label out	= new Label("Расход");
        Label day   	= new Label("День");        Label day2   	= new Label("День");

        gridPane.add(day,0,0);       gridPane.add(day2,3,0);
        gridPane.add(incoming,1,0);  gridPane.add(inc,4,0);
        gridPane.add(outcoming,2,0); gridPane.add(out,5,0);

        AtomicInteger index = new AtomicInteger(1);
        AtomicInteger row = new AtomicInteger(1);
        AtomicInteger row2 = new AtomicInteger(1);

        matrix.forEach((k, v) -> {
            if (index.get() <= 16) {
                gridPane.add(k.get(index.getAndIncrement()), 0, row.get());
                gridPane.add(v.get(0), 1, row.get());
                gridPane.add(v.get(1), 2, row.getAndIncrement());
            } else {
                gridPane.add(k.get(index.getAndIncrement()), 3, row2.get());
                gridPane.add(v.get(0), 4, row2.get());
                gridPane.add(v.get(1), 5, row2.getAndIncrement());
            }
        });
    }

    private void initCalendar(){
        monthBox = new MonthBox();
        monthBox.visibleRowCountProperty().set(6);
        monthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // clear layout
            gridPane.getChildren().clear();
            // launch history for selected month
            initLablesAndFields(historyMap.get(newValue));
            System.out.println(newValue);
        });

        hBoxCalendar.getChildren().add(monthBox);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        show(null);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
