import javafx.application.Application;
import javafx.stage.Stage;
import views.stages.MainStage;

public class Go extends Application{
    private Stage mainStage = new MainStage().getStage();

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage.showAndWait();
    }
}
