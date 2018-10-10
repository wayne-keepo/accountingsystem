import javafx.application.Application;
import javafx.stage.Stage;
import views.stages.MainStage;

import java.awt.*;

public class Go extends Application{
    private Stage mainStage = new MainStage().getStage();

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = dim.width;
        int maxHeight = dim.height;
        mainStage.setResizable(true);
        mainStage.setMaxHeight(maxHeight-50);
        mainStage.setMaxWidth(maxWidth-5);
        mainStage.showAndWait();
    }
}
