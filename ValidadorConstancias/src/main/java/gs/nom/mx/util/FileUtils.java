/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.util;

/**
 *
 * @author acruzb
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author acruzb
 *
 */
public final class FileUtils {
    
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

    public static String readFileFromURI(String uriSource) {
        try {
            LOGGER.info("Obteniendo el archivo");
            URI uri = new URI(uriSource);
            URL url = uri.toURL(); // get URL from your URI object
            try (InputStream is = url.openConnection(Proxy.NO_PROXY).getInputStream()) {
                byte[] bytes = FileCopyUtils.copyToByteArray(is);
                return byteToBase64(bytes);
            }
        }   catch (URISyntaxException | IOException ex) {
            LOGGER.info(String.format("No se ha podido descargar el recuerso %s por favor verificar problemas de conexión.", uriSource));
        } 
        return null;
    }

    public static String byteToBase64(byte[] bytes) throws IOException {
        return new String(Base64.encodeBase64(bytes));
    }
    
    public static String getBase(String file) throws IOException{
        File f=new File(file);
        System.out.println(f.exists());
         try (InputStream is = new FileInputStream(new File(file))) {
                byte[] bytes = FileCopyUtils.copyToByteArray(is);
                return byteToBase64(bytes);
            } catch (IOException ex) {
            LOGGER.info(String.format("No se ha podido descargar el recuerso %s por favor verificar problemas de conexión.", ex));
            throw ex;
        }
    }

}
