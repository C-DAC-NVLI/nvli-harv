//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.29 at 12:36:38 PM IST 
//


package in.gov.nvli.harvester.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deletedRecordType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="deletedRecordType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="no"/>
 *     &lt;enumeration value="persistent"/>
 *     &lt;enumeration value="transient"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "deletedRecordType")
@XmlEnum
public enum DeletedRecordType {

    @XmlEnumValue("no")
    NO("no"),
    @XmlEnumValue("persistent")
    PERSISTENT("persistent"),
    @XmlEnumValue("transient")
    TRANSIENT("transient");
    private final String value;

    DeletedRecordType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeletedRecordType fromValue(String v) {
        for (DeletedRecordType c: DeletedRecordType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
