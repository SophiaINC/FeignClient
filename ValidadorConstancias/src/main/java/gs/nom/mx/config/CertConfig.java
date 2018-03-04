/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author acruzb
 */
@Configuration
public class CertConfig {

    @Autowired
    private ExecuteShellComand shell;

    private static final Logger LOGGER = Logger.getLogger(CertConfig.class);
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String FILE_SEPERATOR = File.separator;

    public static String fileCertName;
    public static boolean certificateInstalled = false;

    @Value("${user.home.folder}")
    private String folderName;
    @Value("${user.home.file.type}")
    private String fileType;
    @Value("${user.home.file.name}")
    private String fileName;
    @Value("${user.home.cert.name}")
    private String certName;
    private Path pathFile;

    public CertConfig() {
    }

    @PostConstruct
    public void init() {
        pathFile = Paths.get(USER_HOME.concat(FILE_SEPERATOR).concat(folderName.toLowerCase()).concat(FILE_SEPERATOR).concat(fileName).concat(fileType));
        fileCertName = certName;//Se asigna el nombre del certificado obtenido del properties.
        if (!shell.excecuteIsCertificateIstalled()) {
            LOGGER.info("Se realiza la instalacion del certificado en el equipo.");
            boolean isCreate = false;
            if (Files.notExists(pathFile)) {
                isCreate = createCertFile();
            } else {
                LOGGER.info("El archivo ya existe en el disco local.");
                isCreate = true;
            }
            if (isCreate) {
                LOGGER.info("Iniciando instalacion de certificado.");
                if (shell.excecuteInstallCertificate(pathFile.toString())) {
                    certificateInstalled = true;
                } else {
                    LOGGER.info("No se pudo instalar el certificado SSL.");
                }
                deleteCertFile();
            }
        } else {
            certificateInstalled = true;
            LOGGER.info("Certificado previamente instalado.");
        }
    }

    private boolean createCertFile() {
        try {
            Files.createDirectories(pathFile.getParent());
            LOGGER.info("Se crea la carpeta contenedora");
            try (InputStream is = getClass().getClassLoader().getResource("certificados/Karalundi_2020.crt").openStream();
                    OutputStream outStream = new FileOutputStream(pathFile.toFile());) {
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                outStream.write(buffer);
                LOGGER.info("Se crea el archivo de certificado: " + pathFile.toString());
                return true;
            } catch (IOException ex) {
                LOGGER.info("No se pudo escribir el archivo.", ex);
            }
        } catch (IOException ex) {
            LOGGER.info("No se pudo crear la carpeta.", ex);
        }
        return false;
    }

    private void deleteCertFile() {
        try {//Se elimina el certificado del disco.
            Files.delete(pathFile);
            LOGGER.info("Se elimina el archivo cert correctamente del disco: " + pathFile.toString());
        } catch (IOException ex) {
            LOGGER.info("No se pudo eliminar el archivo cert de disco.", ex);
        }
    }

}
