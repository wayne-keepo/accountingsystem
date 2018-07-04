package views.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AddButton {
    Button add;


    public AddButton(){create();}

    public Button getAdd() {
        return add;
    }

    public void create(){
        add = new Button("Добавить");
    }

}
