/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_record_metadata_dc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRecordMetadataDc.findAll", query = "SELECT h FROM HarRecordMetadataDc h"),
    @NamedQuery(name = "HarRecordMetadataDc.findByRecordMetadataDcId", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.recordMetadataDcId = :recordMetadataDcId"),
    @NamedQuery(name = "HarRecordMetadataDc.findByCreated", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.created = :created"),
    @NamedQuery(name = "HarRecordMetadataDc.findByUdpated", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.udpated = :udpated"),
    @NamedQuery(name = "HarRecordMetadataDc.findByTitle", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.title = :title"),
    @NamedQuery(name = "HarRecordMetadataDc.findByCreator", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.creator = :creator"),
    @NamedQuery(name = "HarRecordMetadataDc.findByPublisher", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.publisher = :publisher"),
    @NamedQuery(name = "HarRecordMetadataDc.findByContributor", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.contributor = :contributor"),
    @NamedQuery(name = "HarRecordMetadataDc.findByDate", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.date = :date"),
    @NamedQuery(name = "HarRecordMetadataDc.findByType", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.type = :type"),
    @NamedQuery(name = "HarRecordMetadataDc.findByFormat", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.format = :format"),
    @NamedQuery(name = "HarRecordMetadataDc.findByIdentifier", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.identifier = :identifier"),
    @NamedQuery(name = "HarRecordMetadataDc.findBySource", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.source = :source"),
    @NamedQuery(name = "HarRecordMetadataDc.findByLanguage", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.language = :language"),
    @NamedQuery(name = "HarRecordMetadataDc.findByRelation", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.relation = :relation"),
    @NamedQuery(name = "HarRecordMetadataDc.findByCoverage", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.coverage = :coverage"),
    @NamedQuery(name = "HarRecordMetadataDc.findByRights", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.rights = :rights")})
public class HarRecordMetadataDc implements Serializable {

    @Size(max = 500)
    @Column(name = "date")
    private String date;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_metadata_dc_id")
    private Long recordMetadataDcId;
    
    @Column(name = "created",nullable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name = "udpated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date udpated;

    @Size(min = 1, max = 500)
    @Column(name = "title")
    private String title;

    @Size(min = 1, max = 2000)
    @Column(name = "creator")
    private String creator;

    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "subject")
    private String subject;

    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;

    @Size(min = 1, max = 500)
    @Column(name = "publisher")
    private String publisher;

    @Size(min = 1, max = 500)
    @Column(name = "contributor")
    private String contributor;

    @Size(min = 1, max = 500)
    @Column(name = "type")
    private String type;

    @Size(min = 1, max = 500)
    @Column(name = "format")
    private String format;

    @Size(min = 1, max = 500)
    @Column(name = "identifier")
    private String identifier;

    @Size(min = 1, max = 500)
    @Column(name = "source")
    private String source;

    @Size(min = 1, max = 500)
    @Column(name = "language")
    private String language;

    @Size(min = 1, max = 500)
    @Column(name = "relation")
    private String relation;

    @Size(min = 1, max = 500)
    @Column(name = "coverage")
    private String coverage;

    @Size(min = 1, max = 500)
    @Column(name = "rights")
    private String rights;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id")
    @ManyToOne(optional = false)
    private HarRecord recordId;
    

    public HarRecordMetadataDc() {
    }

    public HarRecordMetadataDc(Long recordMetadataDcId) {
        this.recordMetadataDcId = recordMetadataDcId;
    }

    public HarRecordMetadataDc(Long recordMetadataDcId, Date created, Date udpated, String title, String creator, String subject, String description, String publisher, String contributor, String date, String type, String format, String identifier, String source, String language, String relation, String coverage, String rights) {
        this.recordMetadataDcId = recordMetadataDcId;
        this.created = created;
        this.udpated = udpated;
        this.title = title;
        this.creator = creator;
        this.subject = subject;
        this.description = description;
        this.publisher = publisher;
        this.contributor = contributor;
        this.date = date;
        this.type = type;
        this.format = format;
        this.identifier = identifier;
        this.source = source;
        this.language = language;
        this.relation = relation;
        this.coverage = coverage;
        this.rights = rights;
    }

    public Long getRecordMetadataDcId() {
        return recordMetadataDcId;
    }

    public void setRecordMetadataDcId(Long recordMetadataDcId) {
        this.recordMetadataDcId = recordMetadataDcId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUdpated() {
        return udpated;
    }

    public void setUdpated(Date udpated) {
        this.udpated = udpated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public HarRecord getRecordId() {
        return recordId;
    }

    public void setRecordId(HarRecord recordId) {
        this.recordId = recordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordMetadataDcId != null ? recordMetadataDcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRecordMetadataDc)) {
            return false;
        }
        HarRecordMetadataDc other = (HarRecordMetadataDc) object;
        if ((this.recordMetadataDcId == null && other.recordMetadataDcId != null) || (this.recordMetadataDcId != null && !this.recordMetadataDcId.equals(other.recordMetadataDcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRecordMetadataDc[ recordMetadataDcId=" + recordMetadataDcId + " ]";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
