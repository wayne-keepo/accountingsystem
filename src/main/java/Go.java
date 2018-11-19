import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import views.stages.MainStage;

public class Go extends Application{
    private static final Logger logger = LogManager.getLogger(Go.class);

    private Stage mainStage = new MainStage().getStage();

    public static void main(String[] args) {
        logger.info("[Test Log] Run application...");
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
