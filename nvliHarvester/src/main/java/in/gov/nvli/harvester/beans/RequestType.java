//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.29 at 12:36:38 PM IST 
//


package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Define requestType, indicating the protocol request that 
 *       led to the response. Element content is BASE-URL, attributes are arguments 
 *       of protocol request, attribute-values are values of arguments of protocol 
 *       request
 * 
 * <p>Java class for requestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="requestType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anyURI">
 *       &lt;attribute name="verb" type="{http://www.openarchives.org/OAI/2.0/}verbType" />
 *       &lt;attribute name="identifier" type="{http://www.openarchives.org/OAI/2.0/}identifierType" />
 *       &lt;attribute name="metadataPrefix" type="{http://www.openarchives.org/OAI/2.0/}metadataPrefixType" />
 *       &lt;attribute name="from" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType" />
 *       &lt;attribute name="until" type="{http://www.openarchives.org/OAI/2.0/}UTCdatetimeType" />
 *       &lt;attribute name="set" type="{http://www.openarchives.org/OAI/2.0/}setSpecType" />
 *       &lt;attribute name="resumptionToken" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requestType", propOrder = {
    "value"
})
public class RequestType implements Serializable {

    @XmlValue
    @XmlSchemaType(name = "anyURI")
    protected String value;
    @XmlAttribute(name = "verb")
    protected VerbType verb;
    @XmlAttribute(name = "identifier")
    protected String identifier;
    @XmlAttribute(name = "metadataPrefix")
    protected String metadataPrefix;
    @XmlAttribute(name = "from")
    protected String from;
    @XmlAttribute(name = "until")
    protected String until;
    @XmlAttribute(name = "set")
    protected String set;
    @XmlAttribute(name = "resumptionToken")
    protected String resumptionToken;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the verb property.
     * 
     * @return
     *     possible object is
     *     {@link VerbType }
     *     
     */
    public VerbType getVerb() {
        return verb;
    }

    /**
     * Sets the value of the verb property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerbType }
     *     
     */
    public void setVerb(VerbType value) {
        this.verb = value;
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the metadataPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    /**
     * Sets the value of the metadataPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadataPrefix(String value) {
        this.metadataPrefix = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the until property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntil() {
        return until;
    }

    /**
     * Sets the value of the until property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntil(String value) {
        this.until = value;
    }

    /**
     * Gets the value of the set property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSet() {
        return set;
    }

    /**
     * Sets the value of the set property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSet(String value) {
        this.set = value;
    }

    /**
     * Gets the value of the resumptionToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResumptionToken() {
        return resumptionToken;
    }

    /**
     * Sets the value of the resumptionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResumptionToken(String value) {
        this.resumptionToken = value;
    }

}
