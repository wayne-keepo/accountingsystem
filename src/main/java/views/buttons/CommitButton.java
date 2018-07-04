package views.buttons;


import javafx.scene.control.Button;

public class CommitButton {
    Button commit;

    public CommitButton(){create();}

    private void create() {
        commit = new Button("Отправить изменения в базу данных");
    }

    public Button getCommit() {
        return commit;
    }
}
