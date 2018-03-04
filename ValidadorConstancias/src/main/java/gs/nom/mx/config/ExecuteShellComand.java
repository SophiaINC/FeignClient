/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.config;

import static gs.nom.mx.config.CertConfig.fileCertName;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
public class ExecuteShellComand {
    
    private static final Logger LOGGER = Logger.getLogger(ExecuteShellComand.class);
    
    private final String javaHome = System.getProperty("java.home").concat(File.separator);
    private final String osName = System.getProperty("os.name");

    public ExecuteShellComand() {
        
    }

    public boolean excecuteIsCertificateIstalled(){
        String javaCacertDir = javaHome.concat("lib").concat(File.separator).concat("security").concat(File.separator);
        String javaKeytoolDir = javaHome.concat("bin").concat(File.separator);
        Pattern pattern = Pattern.compile("([A-Z]+):.*");
        Matcher matcher = pattern.matcher(javaHome);
        String disco = "C:";
        if(matcher.find()){
            disco = matcher.group(1) + ":";
        }
        //C: && cd C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\ && ..\..\bin\keytool -list -v -keystore cacerts -alias karalundi2002 -storepass changeit
        //ELIMINAR CERT keytool -delete -alias karalundi2020 -keystore cacerts -storepass changeit
        //LISTAR CERT keytool -list -v -alias karalundi2020 -keystore cacerts -storepass changeit
        String[] cmd = new String[3];
        if (osName.contains("Windows")) {
            cmd[0] = "cmd";
            cmd[1] = "/C";
            cmd[2] = String.format("%s && cd %s && %skeytool -list -v -keystore cacerts -alias %s -storepass changeit", disco, javaCacertDir, Paths.get(javaCacertDir).relativize(Paths.get(javaKeytoolDir)).toString().concat(File.separator), fileCertName);
        }
        String resultado = executeCommand(cmd);
        if(!resultado.isEmpty()){
            LOGGER.info("Informacion obtenida: \n"+resultado);
            if(resultado.contains(getSubjectDN())){
                return true;
            }
        }
        return false;
    }
    
    public boolean excecuteInstallCertificate(String pathCert){
        String javaCacertDir = javaHome.concat("lib").concat(File.separator).concat("security").concat(File.separator);
        String javaKeytoolDir = javaHome.concat("bin").concat(File.separator);
        Pattern pattern = Pattern.compile("([A-Z]+):.*");
        Matcher matcher = pattern.matcher(javaHome);
        String disco = "C:";
        if(matcher.find()){
            disco = matcher.group(1) + ":";
        }
        //C: && cd C:\Program Files\Java\jdk1.7.0_79\jre\lib\security\ && ..\..\bin\keytool -import -keystore cacerts -file E:\Users\acruzb\validadorconstancias\Karalundi2020.crt -alias _karalundi2002 -storepass changeit -noprompt
        String[] cmd = new String[3];
        if (osName.contains("Windows")) {
            cmd[0] = "cmd";
            cmd[1] = "/C";
            cmd[2] = String.format("%s && cd %s && %skeytool -import -keystore cacerts -file %s -alias %s -storepass changeit -noprompt", disco, javaCacertDir, Paths.get(javaCacertDir).relativize(Paths.get(javaKeytoolDir)).toString().concat(File.separator), pathCert, fileCertName);
        }
        LOGGER.info("Comando aplicado para la instalacion: " + cmd[2]);
        executeCommand(cmd);
        LOGGER.info("Se valida la instalacion del certificado.");
        if(excecuteIsCertificateIstalled()){//Se verifica el estaus de la instalacion
            LOGGER.info("Se realiza la instalacion exitosa del certificado SSL.");
            return true;
        }
        return false;
    }

    private String executeCommand(String[] command) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.info("No se pudo ejecutar el comando.", e);
        }
        return output.toString();
    }
    
    private String getSubjectDN() {
        String subjectDN = "X.509";
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca;
            try(InputStream is = getClass().getClassLoader().getResource("certificados/Karalundi_2020.crt").openStream();){
                ca = cf.generateCertificate(is);
                subjectDN = ((X509Certificate) ca).getSubjectDN().getName();
                LOGGER.info("Se obtien el nombre del propietaio (interno): " + ((X509Certificate) ca).getSubjectDN());
            }
        } catch (IOException | CertificateException ex) {
            LOGGER.info("No se pudo leer el certificado SSL.", ex);
        } 
        return subjectDN;
    }
    
}
