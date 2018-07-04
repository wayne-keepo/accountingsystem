package views.buttons;

import javafx.scene.control.Button;

public class DeleteButton {
    Button delete;

    public DeleteButton(){
        delete = new Button("Удалить");
        //        create();
    }

    private void create(){
//        delete = new Button("Удалить");
    }

    public Button getDelete() {
        return delete;
    }
}
