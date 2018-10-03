package views.dropBoxes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import utils.enums.RussianMonths;

import java.util.Arrays;
import java.util.LinkedList;

public class MonthBox extends ComboBox<RussianMonths> {

    @Override
    protected ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public MonthBox(){
        super(FXCollections.observableList(new LinkedList<RussianMonths>(Arrays.asList(RussianMonths.values()))));
    }

}
