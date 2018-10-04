package views.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts extends Alert {

    private Alerts(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public static Boolean WARNING_ALERT(String message){
        boolean flag = false;
        ButtonType ok = new Alerts(AlertType.WARNING,message,ButtonType.OK).showAndWait().get();
        if (ok==ButtonType.OK)
            flag = true;
        return flag;
    }

}
