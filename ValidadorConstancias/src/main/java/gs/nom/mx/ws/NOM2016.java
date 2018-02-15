
package gs.nom.mx.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usuarioProveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="constanciaBase64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentoBase64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "usuarioProveedor",
    "password",
    "identificador",
    "constanciaBase64",
    "documentoBase64",
    "nombreDocumento"
})
@XmlRootElement(name = "NOM2016")
public class NOM2016 {

    @XmlElementRef(name = "usuarioProveedor", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> usuarioProveedor;
    @XmlElementRef(name = "password", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> password;
    @XmlElementRef(name = "identificador", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> identificador;
    @XmlElementRef(name = "constanciaBase64", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> constanciaBase64;
    @XmlElementRef(name = "documentoBase64", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> documentoBase64;
    @XmlElementRef(name = "nombreDocumento", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombreDocumento;

    /**
     * Obtiene el valor de la propiedad usuarioProveedor.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUsuarioProveedor() {
        return usuarioProveedor;
    }

    /**
     * Define el valor de la propiedad usuarioProveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUsuarioProveedor(JAXBElement<String> value) {
        this.usuarioProveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad password.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPassword() {
        return password;
    }

    /**
     * Define el valor de la propiedad password.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPassword(JAXBElement<String> value) {
        this.password = value;
    }

    /**
     * Obtiene el valor de la propiedad identificador.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdentificador() {
        return identificador;
    }

    /**
     * Define el valor de la propiedad identificador.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdentificador(JAXBElement<String> value) {
        this.identificador = value;
    }

    /**
     * Obtiene el valor de la propiedad constanciaBase64.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConstanciaBase64() {
        return constanciaBase64;
    }

    /**
     * Define el valor de la propiedad constanciaBase64.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConstanciaBase64(JAXBElement<String> value) {
        this.constanciaBase64 = value;
    }

    /**
     * Obtiene el valor de la propiedad documentoBase64.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDocumentoBase64() {
        return documentoBase64;
    }

    /**
     * Define el valor de la propiedad documentoBase64.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDocumentoBase64(JAXBElement<String> value) {
        this.documentoBase64 = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreDocumento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * Define el valor de la propiedad nombreDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombreDocumento(JAXBElement<String> value) {
        this.nombreDocumento = value;
    }

}
