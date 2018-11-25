package utils.documentGeneration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.enums.Paths;
import utils.enums.Types;
import views.alerts.Alerts;

import java.io.File;
import java.io.IOException;

public class DocumentService {
    private MyQR qrGen;
    private File qr;
    private DocESMG esgm;
    private TheBlankM esgm_m;
    private TheBlankCont esgmv2;
//    private TheBlankM esgm_mv2;
    private String path;
    private String name;
    private String url;


    public DocumentService() throws IOException {
        qrGen = new MyQR();
        esgm = new DocESMG();
        url = "http://elhz.ru/";
        name = "gQR2.jpg";
        path = Paths.C.get()+Paths.ACCOUNTING_SYSTEM.get()+Paths.DOCUMENTS.get();
        qr = new File(path,name);
        if (!qr.exists())
            qrGen.theQR(path,name,url);
    }

    public void generateDocumentByType(String type,
                                       String elNumberFrom,
                                       String elNumberTo,
                                       String cableLength,
                                       String empoyerPosition,
                                       String fullName,
                                       String createDate){

        Types _type = Types.valueOf(type);
        try {
            switch (_type){
                case ESMG:
                    esgm.theDoc(elNumberFrom,elNumberTo,cableLength,empoyerPosition,fullName,createDate,qr);
                    break;
                case ESMG_M:
                    esgm_m.theDocM(elNumberFrom,elNumberTo,cableLength,empoyerPosition,fullName,createDate,qr);
                    break;
            }

        } catch (InvalidFormatException | IOException e) {
            Alerts.WARNING_ALERT(String.format("Произошла ошибка при генерации %s дкоумента.",type));
            e.printStackTrace();
        }

    }

}
