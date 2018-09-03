package views.dropBoxes;

import databaselogic.controllers.DBDetailController;
import entities.Detail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import services.DetailService;

public class DetailDropBox {
    private ComboBox<Detail> detailsBox;
    private ObservableList<Detail> details;

    public DetailDropBox(){
        details = FXCollections.observableArrayList();
        details.addAll(DetailService.getAll());
        create();
    }

    private void create() {
        detailsBox = new ComboBox<>();
        detailsBox.setPromptText("Выберите деталь...");
        fillBox(detailsBox);
    }

    private void fillBox(ComboBox<Detail> box){
        for (Detail detail: details)
            box.getItems().add(detail);
    }
    public void deleteDetail(Detail detail){
        detailsBox.getItems().remove(detail);
    }
    public void addDetail(Detail detail){
        detailsBox.getItems().add(detail);
    }
    public ComboBox<Detail> getDetailsBox() {
        return detailsBox;
    }
}
