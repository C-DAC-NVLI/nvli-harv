/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Bhumika
 */
@XmlRootElement(name="oai-identifier")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepositoryDescriptionType", propOrder = {
    "xmlns",
    "xsi",
    "schemaLocation",
     "scheme",
    "repositoryIdentifier",
    "delimiter",
    "sampleIdentifier"
})

public class RepositoryDescriptionType {

    @XmlAttribute
    protected String xmlns;
    @XmlAttribute(name="xmlns:xsi")
    protected String xsi;
    @XmlAttribute(name="xsi:schemaLocation")
    protected String schemaLocation; 
    @XmlElement
    protected String scheme;
    @XmlElement
    protected String repositoryIdentifier;
    @XmlElement
    protected String delimiter;  
    @XmlElement
    protected String sampleIdentifier;

     public String getXmlns() {
        return xmlns;
    }
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
    public String getXsi() {
        return xsi;
    }
    public void setXsi(String xsi) {
        this.xsi = xsi;
    }
    public String getSchemaLocation() {
        return schemaLocation;
    }
    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
      public String getScheme() {
        return scheme;
    }
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
    public String getRepositoryIdentifier() {
        return repositoryIdentifier;
    }
    public void setRepositoryIdentifier(String repositoryIdentifier) {
        this.repositoryIdentifier = repositoryIdentifier;
    }
    public String getDelimiter() {
        return delimiter;
    }
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
    public String getSampleIdentifier() {
        return sampleIdentifier;
    } 
    public void setSampleIdentifier(String sampleIdentifier) {
        this.sampleIdentifier = sampleIdentifier;
    }
}
