package views.dropBoxes;

import databaselogic.controllers.DBDetailController;
import entities.Detail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class DetailDropBox {
    private ComboBox<Detail> detailsBox;
    private ObservableList<Detail> details;
    private final DBDetailController controller = new DBDetailController();

    public DetailDropBox(){
        details = FXCollections.observableArrayList();
        details.addAll(controller.getAll());
        create();
    }

    private void create() {
        detailsBox = new ComboBox<>();
        detailsBox.setPromptText("Выберите деталь...");
        fillBox(detailsBox);
//        detailsBox.setItems(InitializerForTest.getDetails());
    }

    private void fillBox(ComboBox<Detail> box){
        for (Detail detail: details)
            box.getItems().add(detail);
    }

    public ComboBox<Detail> getDetailsBox() {
        return detailsBox;
    }
}
