/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRecord.findAll", query = "SELECT h FROM HarRecord h"),
    @NamedQuery(name = "HarRecord.findByRecordId", query = "SELECT h FROM HarRecord h WHERE h.recordId = :recordId"),
    @NamedQuery(name = "HarRecord.findByIdentifier", query = "SELECT h FROM HarRecord h WHERE h.identifier = :identifier"),
    @NamedQuery(name = "HarRecord.findBySoureDatestamp", query = "SELECT h FROM HarRecord h WHERE h.soureDatestamp = :soureDatestamp")})
public class HarRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_id")
    private Long recordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "identifier")
    private String identifier;
    @Basic(optional = false)
    @NotNull
    @Column(name = "soure_datestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date soureDatestamp;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "about")
    private String about;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<HarRecordMetadataDc> harRecordMetadataDcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<HarRecordOriginalxml> harRecordOriginalxmlCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<HarRecordLanguage> harRecordLanguageCollection;
    @JoinColumn(name = "metadata_type_id", referencedColumnName = "metadata_id")
    @ManyToOne(optional = false)
    private HarMetadataType metadataTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
    private Collection<HarSetRecord> harSetRecordCollection;

    public HarRecord() {
    }

    public HarRecord(Long recordId) {
        this.recordId = recordId;
    }

    public HarRecord(Long recordId, String identifier, Date soureDatestamp, String about) {
        this.recordId = recordId;
        this.identifier = identifier;
        this.soureDatestamp = soureDatestamp;
        this.about = about;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getSoureDatestamp() {
        return soureDatestamp;
    }

    public void setSoureDatestamp(Date soureDatestamp) {
        this.soureDatestamp = soureDatestamp;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @XmlTransient
    public Collection<HarRecordMetadataDc> getHarRecordMetadataDcCollection() {
        return harRecordMetadataDcCollection;
    }

    public void setHarRecordMetadataDcCollection(Collection<HarRecordMetadataDc> harRecordMetadataDcCollection) {
        this.harRecordMetadataDcCollection = harRecordMetadataDcCollection;
    }

    @XmlTransient
    public Collection<HarRecordOriginalxml> getHarRecordOriginalxmlCollection() {
        return harRecordOriginalxmlCollection;
    }

    public void setHarRecordOriginalxmlCollection(Collection<HarRecordOriginalxml> harRecordOriginalxmlCollection) {
        this.harRecordOriginalxmlCollection = harRecordOriginalxmlCollection;
    }

    @XmlTransient
    public Collection<HarRecordLanguage> getHarRecordLanguageCollection() {
        return harRecordLanguageCollection;
    }

    public void setHarRecordLanguageCollection(Collection<HarRecordLanguage> harRecordLanguageCollection) {
        this.harRecordLanguageCollection = harRecordLanguageCollection;
    }

    public HarMetadataType getMetadataTypeId() {
        return metadataTypeId;
    }

    public void setMetadataTypeId(HarMetadataType metadataTypeId) {
        this.metadataTypeId = metadataTypeId;
    }

    @XmlTransient
    public Collection<HarSetRecord> getHarSetRecordCollection() {
        return harSetRecordCollection;
    }

    public void setHarSetRecordCollection(Collection<HarSetRecord> harSetRecordCollection) {
        this.harSetRecordCollection = harSetRecordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRecord)) {
            return false;
        }
        HarRecord other = (HarRecord) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRecord[ recordId=" + recordId + " ]";
    }
    
}
