package views.dropBoxes;

import entities.Detail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import services.DetailService;

import java.util.List;
import java.util.Observable;

public class DetailDropBox extends Observable {
    private ComboBox<Detail> detailsBox;
    private ObservableList<Detail> details;

    public DetailDropBox(){
        details = FXCollections.observableArrayList();
        List<Detail> tmp = DetailService.getAll();
        if (tmp!=null)
            details.addAll(tmp);
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
