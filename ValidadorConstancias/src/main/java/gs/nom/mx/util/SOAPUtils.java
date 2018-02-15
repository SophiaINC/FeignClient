/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author acruzb
 */
public class SOAPUtils {

    private static final Logger LOGGER = Logger.getLogger(SOAPUtils.class);
    private static final String PREFIX = "soapenv";
    private static final int TIME_OUT;
    
    static{
        TIME_OUT = 300 * 1000;
    }
    
    public SOAPUtils() {
        super();
    }

    /**
     * Crea una conexion http(s) con la configuracion indicada
     *
     * @param url
     * @param withProxy
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public HttpURLConnection createConnection(String url, boolean withProxy)
            throws MalformedURLException, IOException {
        LOGGER.info("Se crea el objeto de conexion con el proveedor: " + url);
        URL urlAction = new URL(url);
        disableCertificateValidation();
        Proxy proxy = (Proxy) ((withProxy) ? ProxyUtils.getProxyDevelopment() : Proxy.NO_PROXY);
        HttpsURLConnection connection = (HttpsURLConnection) urlAction.openConnection(proxy);
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        return connection;
    }

    /**
     * Agrega el Action a una conexion declarada
     *
     * @param connection
     * @param soapAction
     * @throws ProtocolException
     */
    public void addAction(HttpURLConnection connection, String soapAction) throws ProtocolException {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setRequestProperty("soapAction", soapAction);
    }

    /**
     * Crea un objeto para parsear de objeto java a xml
     *
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public Marshaller createMarshaller(Class<?> clazz) throws JAXBException {
        JAXBContext contextForRequest = JAXBContext.newInstance(clazz);
        Marshaller m = contextForRequest.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty("jaxb.fragment", Boolean.TRUE);
        return m;
    }

    /**
     * Crea un objeto para parsear de xml a objeto java
     *
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public Unmarshaller createUnmarshaller(Class<?> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz).createUnmarshaller();
    }

    /**
     * Crea un soap message a partir de un parseador de xml y el objeto
     *
     * @param m
     * @param elem
     * @return
     * @throws ParserConfigurationException
     * @throws SOAPException
     * @throws IOException
     * @throws JAXBException
     */
    public String createSoapMessageAsString(Marshaller m, Object elem)
            throws ParserConfigurationException, SOAPException, IOException, JAXBException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        m.marshal(elem, document);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
        soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration(PREFIX,
                "http://schemas.xmlsoap.org/soap/envelope/");
        soapMessage.getSOAPPart().getEnvelope().setPrefix(PREFIX);
        soapMessage.getSOAPHeader().setPrefix(PREFIX);
        soapMessage.getSOAPBody().setPrefix(PREFIX);
        soapMessage.getSOAPBody().addDocument(document);
        soapMessage.writeTo(outputStream);

        String message = new String(outputStream.toByteArray());
//        LOGGER.info("SoapMessage a enviar: " + message);
        return new String(message);
    }

    /**
     * Crea un DOMSource a partir de un input stream
     *
     * @param is
     * @return
     * @throws IOException
     * @throws SOAPException
     */
    public DOMSource createDOMSourceFromInputStream(InputStream is) throws IOException, SOAPException {
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.mark(0);
        SOAPMessage sm = MessageFactory.newInstance().createMessage(null, bis);
        SOAPBody sb = sm.getSOAPBody();
        Document doc = sb.extractContentAsDocument();
        LOGGER.info("WS Response OK");
        bis.reset();
//        LOGGER.info("SoapMessage recibido: " + IOUtils.toString(bis, "UTF-8"));
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException e) {
            }
        }
        return new DOMSource(doc);
    }

    /**
     * Crea un SOAPFault a partir de un input stream
     *
     * @param is
     * @return
     * @throws SOAPException
     * @throws IOException
     */
    public SOAPFault createSOAPFaultFromInputStream(InputStream is) throws SOAPException, IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.mark(0);
        SOAPMessage sm = MessageFactory.newInstance().createMessage(null, bis);
        bis.reset();
        LOGGER.info("Response Fault: " + IOUtils.toString(bis, "UTF-8"));
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException e) {
            }
        }
        return sm.getSOAPBody().getFault();
    }

    
    public static void disableCertificateValidation() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
            @Override
            public X509Certificate[] getAcceptedIssuers(){return null;}
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
        }
    }

}
