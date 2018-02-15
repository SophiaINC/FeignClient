package gs.nom.mx.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import gs.nom.mx.util.SOAPUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidadorNOMImp {

    private static final Logger LOGGER = Logger.getLogger(ValidadorNOMImp.class);

    @Value("${ws.produccion.url}")
    public String wsURLService;
    @Value("${ws.produccion.user}")
    public String wsUSERService;
    @Value("${ws.produccion.pwd}")
    public String wsPWDService;

    private final SOAPUtils soapUtils;
    private HttpURLConnection connection;
    private Marshaller marshaller;
    private Unmarshaller unMarshaller;

    private static final String ACTION_VALIDA_NOM2002 = "http://tempuri.org/IwsValida/NOM2002";
    private static final String ACTION_VALIDA_NOM2016 = "http://tempuri.org/IwsValida/NOM2016";

    private final ObjectFactory factory;

    public ValidadorNOMImp() {
        factory = new ObjectFactory();
        soapUtils = new SOAPUtils();
        try {
            marshaller = soapUtils.createMarshaller(ObjectFactory.class);
            unMarshaller = soapUtils.createUnmarshaller(ObjectFactory.class);
        } catch (JAXBException e) {
            LOGGER.info("No se han podido generar los parseadores xml.");
        }
    }

    private synchronized void openNewConnection() throws MalformedURLException, IOException {
        connection = soapUtils.createConnection(wsURLService, true);
    }

    public synchronized String validaNOM2002(String idDoc, String name, String docB64, String constancia)
            throws MalformedURLException, IOException, ParserConfigurationException, SOAPException, JAXBException {
        String nom151 = null;
        NOM2002 elem = factory.createNOM2002();
        elem.setPassword(factory.createNOM2002Password(wsPWDService));
        elem.setUsuarioProveedor(factory.createNOM2002UsuarioProveedor(wsUSERService));
        elem.setIdentificador(factory.createNOM2002Identificador(idDoc));
        elem.setNombreDocumento(factory.createNOM2002NombreDocumento(name));
        elem.setDocumentoBase64(factory.createNOM2002DocumentoBase64(docB64));
        elem.setConstanciaBase64(factory.createNOM2002ConstanciaBase64(constancia));

        openNewConnection();

        soapUtils.addAction(connection, ACTION_VALIDA_NOM2002);
        String soapMessage = soapUtils.createSoapMessageAsString(marshaller, elem);
        OutputStream os = null;
        InputStream is = null;
        try {
            os = connection.getOutputStream();
            // Realiza el envio
            os.write(soapMessage.getBytes());
            switch (connection.getResponseCode()) {
                case 200:
                    is = connection.getInputStream();
                    DOMSource ds = soapUtils.createDOMSourceFromInputStream(is);
                    NOM2002Response response = (NOM2002Response) unMarshaller.unmarshal(ds);
                    LOGGER.info("Se ha validado la NOM151 --> " + response.getNOM2002Result().getValue());
                    XmlMapper xmlMapper = new XmlMapper();
                    JsonNode json = xmlMapper.readTree(response.getNOM2002Result().getValue());
                    ObjectMapper mapper = new ObjectMapper();
                    nom151 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    break;
                case 500:
                    is = connection.getErrorStream();
                    SOAPFault sf = soapUtils.createSOAPFaultFromInputStream(is);
                    nom151 = "No se ha podido validar la contancia debido a: " + sf.getFaultString();
                    LOGGER.info("No se ha podido validar la contancia debido a: " + sf.getFaultString());
                    break;
                default:
                    LOGGER.info("Fallo en el servicio de Karalundi codigo: " + connection.getResponseCode() + "Descripcion: " + connection.getResponseMessage());
                    break;
            }
        } finally {
            closeOutputStream(os);// se cierra la conexion
            closeInputStream(is);// cierra conexion
        }
        return nom151;
    }

    public synchronized String validaNOM2016(String idDoc, String name, String docB64, String constancia)
            throws IOException, ParserConfigurationException, SOAPException, JAXBException {
        String nom151 = null;
        NOM2016 elem = factory.createNOM2016();
        elem.setPassword(factory.createNOM2002Password(wsPWDService));
        elem.setUsuarioProveedor(factory.createNOM2016UsuarioProveedor(wsUSERService));
        elem.setIdentificador(factory.createNOM2016Identificador(idDoc));
        elem.setNombreDocumento(factory.createNOM2016NombreDocumento(name));
        elem.setDocumentoBase64(factory.createNOM2016DocumentoBase64(docB64));
        elem.setConstanciaBase64(factory.createNOM2016ConstanciaBase64(constancia));

        openNewConnection();

        soapUtils.addAction(connection, ACTION_VALIDA_NOM2016);
        String soapMessage = soapUtils.createSoapMessageAsString(marshaller, elem);
        OutputStream os = null;
        InputStream is = null;
        try {
            os = connection.getOutputStream();
            // Realiza el envio
            os.write(soapMessage.getBytes());
            switch (connection.getResponseCode()) {
                case 200:
                    is = connection.getInputStream();
                    DOMSource ds = soapUtils.createDOMSourceFromInputStream(is);
                    NOM2016Response response = (NOM2016Response) unMarshaller.unmarshal(ds);
                    LOGGER.info("Se ha validado la NOM151 --> " + response.getNOM2016Result().getValue());
                    XmlMapper xmlMapper = new XmlMapper();
                    JsonNode json = xmlMapper.readTree(response.getNOM2016Result().getValue());
                    ObjectMapper mapper = new ObjectMapper();
                    nom151 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    break;
                case 500:
                    is = connection.getErrorStream();
                    SOAPFault sf = soapUtils.createSOAPFaultFromInputStream(is);
                    LOGGER.info("No se ha obtenido la NOM151 debido a: " + sf.getFaultString());
                    break;
                default:
                    LOGGER.info("Fallo en el servicio de Karalundi codigo: " + connection.getResponseCode() + "Descripcion: " + connection.getResponseMessage());
                    break;
            }
        } finally {
            closeOutputStream(os);// se cierra la conexion
            closeInputStream(is);// cierra conexion
        }
        return nom151;
    }

    private synchronized void closeInputStream(InputStream is) {
        try {
            if (null != is) {
                is.close();
            }
        } catch (IOException e) {
            LOGGER.info("No se han podido cerrar la conexion.");
        }
    }

    private synchronized void closeOutputStream(OutputStream os) {
        try {
            if (null != os) {
                os.close();
            }
        } catch (IOException e) {
            LOGGER.info("No se han podido cerrar la conexion.");
        }
    }

}
