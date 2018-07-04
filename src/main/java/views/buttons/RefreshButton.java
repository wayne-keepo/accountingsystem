package views.buttons;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class RefreshButton<T> {
    private Button refresh;
    private TableView<T> table;

    public RefreshButton(TableView<T> table) {
        this.table=table;
        create();
    }

    public Button getRefresh() {
        return refresh;
    }

    public void create() {
        refresh = new Button("Обновить таблицу");
    }

    public void action(ObservableList<T> list){
        refresh.setOnAction(event -> {
            table.getItems().addAll(list);
        });
    }

}
