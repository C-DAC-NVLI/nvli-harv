/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package in.gov.nvli.harvester.beans;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author richa
 */
public class OAIDC implements Serializable{
    private List<String> title;
    private List<String> creator;
    private List<String> subject;
    private List<String> description;
    private List<String> date;
    private List<String> Publisher;
    private List<String> Contributor;
    private List<String> Type;
    private List<String> Format;
    private List<String> Identifier;
    private List<String> Source;
    private List<String> Language;
    private List<String> Relation;
    private List<String> Coverage;
    private List<String> Rights;
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getCreator() {
        return creator;
    }

    public void setCreator(List<String> creator) {
        this.creator = creator;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getSubject() {
        return subject;
    }
    public void setSubject(List<String> subject) {
        this.subject = subject;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getDescription() {
        return description;
    }
    public void setDescription(List<String> description) {
        this.description = description;
    }
 @XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getPublisher() {
        return Publisher;
    }

    public void setPublisher(List<String> Publisher) {
        this.Publisher = Publisher;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getContributor() {
        return Contributor;
    }

    public void setContributor(List<String> Contributor) {
        this.Contributor = Contributor;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getType() {
        return Type;
    }

    public void setType(List<String> Type) {
        this.Type = Type;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getFormat() {
        return Format;
    }

    public void setFormat(List<String> Format) {
        this.Format = Format;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(List<String> Identifier) {
        this.Identifier = Identifier;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getSource() {
        return Source;
    }

    public void setSource(List<String> Source) {
        this.Source = Source;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getLanguage() {
        return Language;
    }

    public void setLanguage(List<String> Language) {
        this.Language = Language;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getRelation() {
        return Relation;
    }

    public void setRelation(List<String> Relation) {
        this.Relation = Relation;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getCoverage() {
        return Coverage;
    }

    public void setCoverage(List<String> Coverage) {
        this.Coverage = Coverage;
    }
@XmlElement(required = true,namespace = "http://purl.org/dc/elements/1.1/")
    public List<String> getRights() {
        return Rights;
    }

    public void setRights(List<String> Rights) {
        this.Rights = Rights;
    }

    @Override
    public String toString() {
        return "OAIDC{" + "title=" + title + ", creator=" + creator + ", subject=" + subject + ", description=" + description + ", date=" + date + ", Publisher=" + Publisher + ", Contributor=" + Contributor + ", Type=" + Type + ", Format=" + Format + ", Identifier=" + Identifier + ", Source=" + Source + ", Language=" + Language + ", Relation=" + Relation + ", Coverage=" + Coverage + ", Rights=" + Rights + '}';
    }
    
}
