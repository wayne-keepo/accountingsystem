package utils.documentGeneration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.enums.Paths;
import utils.enums.Types;
import views.alerts.Alerts;

import java.io.File;
import java.io.IOException;

public class DocumentService {
    private File qr;
    private DocESMG esmg; // part 10.
    private DocESMGM esmg_m; // part 10.
    private DocESMGM_V2 esmgm_v2; // part 11.
    private DocESMG_V2 esmg_v2; // part 11.

    public DocumentService() throws IOException {
        esmg = new DocESMG();
        esmg_v2 = new DocESMG_V2();
        esmg_m = new DocESMGM();
        esmgm_v2 = new DocESMGM_V2();

        String url = "http://elhz.ru/";
        String name = "gQR2.jpg";
        String path = Paths.C.get() + Paths.ACCOUNTING_SYSTEM.get() + Paths.DOCUMENTS.get();

        qr = new File(path, name);
        if (!qr.exists())
            new MyQR().theQR(path, name, url);
    }

    public void generateDocumentByType(String type,
                                       String elNumberFrom,
                                       String elNumberTo,
                                       String cableLength,
                                       String empoyerPosition,
                                       String fullName,
                                       String createDate){

        try {

            if (Types.ESMG.eng().equalsIgnoreCase(type)){
                esmg.theDoc(elNumberFrom,elNumberTo,cableLength,empoyerPosition,fullName,createDate,qr);
                esmg_v2.theDocCont(elNumberFrom,elNumberTo,cableLength,empoyerPosition,fullName,createDate,qr);
            }
            if (Types.ESMG_M.eng().equalsIgnoreCase(type)) {
                esmg_m.theDocM(elNumberFrom, elNumberTo, cableLength, empoyerPosition, fullName, createDate, qr);
                esmgm_v2.theDocCont(elNumberFrom, elNumberTo, cableLength, empoyerPosition, fullName, createDate, qr);
            }
        } catch (InvalidFormatException | IOException e1) {
            Alerts.WARNING_ALERT(String.format("Произошла ошибка при генерации %s дкоумента.",type));
            e1.printStackTrace();
        }
    }
}
