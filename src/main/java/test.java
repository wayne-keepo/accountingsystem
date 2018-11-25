import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.documentGeneration.DocESMG;
import utils.documentGeneration.MyQR;

import java.io.IOException;

public class test {
    private static final Logger logger = LogManager.getLogger(test.class);

    public static void main(String[] args) throws InterruptedException, IOException, InvalidFormatException {

        new MyQR().theQR("lalalal");
        new DocESMG().theDoc("100", "200", "1, 2, 3, 4, 10, 5", "Test", "test", "22.08.1995");

//        logger.info("Start application..");
//        logger.info("[Counting] Start counting..");
//        for (int i=0;i<2;i++){
//            logger.info("   test counter for test log {} ",i);
//            logger.debug("   test counter for test log = {}",i);
//        }
//        logger.info("[Counting] End counting..");
//        logger.info("End application..");

    }
}
