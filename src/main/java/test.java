import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.documentGeneration.DocumentService;

import java.io.IOException;

public class test {
    private static final Logger logger = LogManager.getLogger(test.class);

    public static void main(String[] args) throws InterruptedException, IOException, InvalidFormatException {
        DocumentService documents = new DocumentService();

        documents.generateDocumentByType(
                "ESMG",
                "100",
                "200",
                "1, 2, 3, 4, 10, 5",
                "Test",
                "test",
                "22.08.1995"
        );

        Thread.sleep(2*1000);


        documents.generateDocumentByType(
                "ESMG-M",
                "100",
                "200",
                "1, 2, 3, 4, 10, 5",
                "Test",
                "test",
                "22.08.1995"
        );

    }
}
