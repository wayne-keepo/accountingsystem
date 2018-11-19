import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class test {
    private static final Logger logger = LogManager.getLogger(test.class);

    public static void main(String[] args) throws InterruptedException {

        for (int i=0;i<1000;i++){
            logger.info("test counter for test log = "+i);
            logger.debug("[Debug] test counter for test log = {}",i);
            Thread.sleep(100);
        }

    }
}
