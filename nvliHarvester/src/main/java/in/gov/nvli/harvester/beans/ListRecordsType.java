//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.29 at 12:36:38 PM IST 
//


package in.gov.nvli.harvester.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListRecordsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListRecordsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="record" type="{http://www.openarchives.org/OAI/2.0/}recordType" maxOccurs="unbounded"/>
 *         &lt;element name="resumptionToken" type="{http://www.openarchives.org/OAI/2.0/}resumptionTokenType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRecordsType", propOrder = {
    "record",
    "resumptionToken"
})
public class ListRecordsType {

    @XmlElement(required = true)
    protected List<RecordType> record;
    protected ResumptionTokenType resumptionToken;

    /**
     * Gets the value of the record property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the record property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecordType }
     * 
     * 
     */
    public List<RecordType> getRecord() {
        if (record == null) {
            record = new ArrayList<RecordType>();
        }
        return this.record;
    }

    public void setRecord(List<RecordType> record) {
        this.record = record;
    }

    /**
     * Gets the value of the resumptionToken property.
     * 
     * @return
     *     possible object is
     *     {@link ResumptionTokenType }
     *     
     */
    public ResumptionTokenType getResumptionToken() {
        return resumptionToken;
    }

    /**
     * Sets the value of the resumptionToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResumptionTokenType }
     *     
     */
    public void setResumptionToken(ResumptionTokenType value) {
        this.resumptionToken = value;
    }

}
