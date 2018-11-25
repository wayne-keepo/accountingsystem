package utils.documentGeneration;

import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MyQR {

    public void theQR(String path, String name, String slovo) throws IOException {

        File f = new File(path,name);
        try(
                ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(slovo).to(ImageType.PNG).stream();
                FileOutputStream fos = new FileOutputStream(f)
        ){
            fos.write(out.toByteArray());
            fos.flush();
        }
    }

}

