import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class test {
    private static final Logger logger = LogManager.getLogger(test.class);

    public static void main(String[] args) throws InterruptedException {

        logger.info("Start application..");
        logger.info("[Counting] Start counting..");
        for (int i=0;i<2;i++){
            logger.info("   test counter for test log {} ",i);
            logger.debug("   test counter for test log = {}",i);
        }
        logger.info("[Counting] End counting..");
        logger.info("End application..");

    }
}
