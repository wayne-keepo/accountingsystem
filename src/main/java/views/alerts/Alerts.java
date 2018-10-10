package views.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts extends Alert {

    private Alerts(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public static void WARNING_ALERT(String message){
        new Alerts(AlertType.WARNING,message,ButtonType.OK).showAndWait();
    }

}
