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
  @NamedQuery(name = "HarRecord.findByRecordIdentifier", query = "SELECT h FROM HarRecord h WHERE h.recordIdentifier = :recordIdentifier"),
  @NamedQuery(name = "HarRecord.findByRecordSoureDatestamp", query = "SELECT h FROM HarRecord h WHERE h.recordSoureDatestamp = :recordSoureDatestamp")})
public class HarRecord implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "record_id", nullable = false)
  private Long recordId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "record_identifier", nullable = false, length = 255)
  private String recordIdentifier;
  @Column(name = "record_soure_datestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date recordSoureDatestamp;
  @Lob
  @Size(max = 65535)
  @Column(name = "record_about", length = 65535)
  private String recordAbout;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
  private Collection<HarRecordMetadataDc> harRecordMetadataDcCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
  private Collection<HarRecordOriginalxml> harRecordOriginalxmlCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
  private Collection<HarRecordLanguage> harRecordLanguageCollection;
  @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
  @ManyToOne(optional = false)
  private HarRepo repoId;
  @JoinColumn(name = "metadata_type_id", referencedColumnName = "metadata_id", nullable = false)
  @ManyToOne(optional = false)
  private HarMetadataType metadataTypeId;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
  private Collection<HarSetRecord> harSetRecordCollection;
  @Basic(optional = false)
  @Column(name = "record_status", nullable = false, columnDefinition =  "smallint default 0")
  private Short recordStatus;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordId")
  private Collection<HarRecordData> harRecordDataCollection;

  public HarRecord() {
  }

  public HarRecord(Long recordId) {
    this.recordId = recordId;
  }

  public HarRecord(Long recordId, String recordIdentifier) {
    this.recordId = recordId;
    this.recordIdentifier = recordIdentifier;
  }

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public String getRecordIdentifier() {
    return recordIdentifier;
  }

  public void setRecordIdentifier(String recordIdentifier) {
    this.recordIdentifier = recordIdentifier;
  }

  public Date getRecordSoureDatestamp() {
    return recordSoureDatestamp;
  }

  public void setRecordSoureDatestamp(Date recordSoureDatestamp) {
    this.recordSoureDatestamp = recordSoureDatestamp;
  }

  public String getRecordAbout() {
    return recordAbout;
  }

  public void setRecordAbout(String recordAbout) {
    this.recordAbout = recordAbout;
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

  public HarRepo getRepoId() {
    return repoId;
  }

  public void setRepoId(HarRepo repoId) {
    this.repoId = repoId;
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

  public Short getRecordStatus() {
    return recordStatus;
  }

  public void setRecordStatus(Short recordStatus) {
    this.recordStatus = recordStatus;
  }
  
  @XmlTransient
    public Collection<HarRecordData> getHarRecordDataCollection() {
        return harRecordDataCollection;
    }

    public void setHarRecordDataCollection(Collection<HarRecordData> harRecordDataCollection) {
        this.harRecordDataCollection = harRecordDataCollection;
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
