package utils.documentGeneration;

import net.glxn.qrgen.image.ImageType;
import utils.enums.Paths;

import java.io.*;


public class MyQR {

    public void theQR(String slovo)   {
        String path = Paths.C.get()+Paths.ACCOUNTING_SYSTEM.get()+Paths.DOCUMENTS.get();
        String name = "gQR2.jpg";
        File f = new File(path,name);
        try(FileOutputStream fos = new FileOutputStream(f)) {
            ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(slovo).to(ImageType.PNG).stream();
            fos.write(out.toByteArray());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

