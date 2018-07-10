package views.dropBoxes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import views.tables.utils.RussianMonths;

import java.time.Month;
import java.util.Arrays;
import java.util.LinkedList;

public class MonthBox extends ComboBox<RussianMonths> {
    private static final ObservableList<RussianMonths> months
            = FXCollections.observableList(new LinkedList<RussianMonths>(Arrays.asList(RussianMonths.values())));


    @Override
    protected ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public MonthBox(){
        super();
        this.getItems().clear();
        this.getItems().addAll(months);
    }

}
