//package views.scens;
//
//import entities.Detail;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.BorderPane;
//
//
//public class MainScene {
//    private Scene scene;
//    private BorderPane pane;
//    private TableView<Detail> table;
//    private ObservableList<Detail> details = FXCollections.observableArrayList();
//
//    private void initialize(){
//        pane = new BorderPane();
//        Button button = new Button("Test button");
//
//        pane.setCenter(createTabPane());
//        pane.setBottom(button);
//        scene = new Scene(pane);
//    }
//
//    public Scene getScene() {
//        initialize();
//        return scene;
//    }
//
//    private TabPane createTabPane(){
//        Tab accounting = new Tab("Учет");
//        Tab product = new Tab("Изделие");
//
//        accounting.setContent(createTable());
//
//        TabPane tabPane = new TabPane(accounting,product);
//
//        return tabPane;
//    }
//
//    private TableView<Detail> createTable(){
//        table = new TableView<Detail>();
//
//        TableColumn title = new TableColumn("Title");
//        title.setCellValueFactory(new PropertyValueFactory<Detail,String>("title"));
//
//        TableColumn count = new TableColumn("Count");
//        count.setCellValueFactory(new PropertyValueFactory<Detail,Integer>("count"));
//
//        TableColumn descriptions = new TableColumn("Descriptions");
//        descriptions.setCellValueFactory(new PropertyValueFactory<Detail,String>("descriptions"));
//
//        table.getColumns().addAll(title,count,descriptions);
//
//        table.setEditable(true);
//        fillTable(table);
//
//        return table;
//
//    }
//
//    private ObservableList<Detail> fillTable(TableView table){
//        details.addAll(
//                new Detail(new SimpleStringProperty("Гайка"),new SimpleIntegerProperty(333),null),
//                new Detail(new SimpleStringProperty("Болт"),new SimpleIntegerProperty(222),null),
//                new Detail(new SimpleStringProperty("Ветка"),new SimpleIntegerProperty(111),null),
//                new Detail(new SimpleStringProperty("Инок раб Бога"),new SimpleIntegerProperty(666),null)
//        );
//        table.getItems().addAll(details);
//        return details;
//    }
//
//}
