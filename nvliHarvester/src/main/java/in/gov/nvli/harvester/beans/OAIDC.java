/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package in.gov.nvli.harvester.beans;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author richa
 */
public class OAIDC implements Serializable{
    private String title;
    private String creator;
    private String subject;
    private String description;
   
    private String date;
    private String Publisher;
    private String Contributor;
    private String Type;
    private String Format;
    private String Identifier;
    private String Source;
    private String Language;
    private String Relation;
    private String Coverage;
    private String Rights;
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
 @XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getContributor() {
        return Contributor;
    }

    public void setContributor(String Contributor) {
        this.Contributor = Contributor;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getFormat() {
        return Format;
    }

    public void setFormat(String Format) {
        this.Format = Format;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getRelation() {
        return Relation;
    }

    public void setRelation(String Relation) {
        this.Relation = Relation;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getCoverage() {
        return Coverage;
    }

    public void setCoverage(String Coverage) {
        this.Coverage = Coverage;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public String getRights() {
        return Rights;
    }

    public void setRights(String Rights) {
        this.Rights = Rights;
    }
}
