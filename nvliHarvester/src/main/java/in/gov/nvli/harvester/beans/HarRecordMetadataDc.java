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
    @NamedQuery(name = "HarRecordMetadataDc.findByRowAdded", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.rowAdded = :rowAdded"),
    @NamedQuery(name = "HarRecordMetadataDc.findByRowUdpated", query = "SELECT h FROM HarRecordMetadataDc h WHERE h.rowUdpated = :rowUdpated")})
public class HarRecordMetadataDc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_metadata_dc_id", nullable = false)
    private Long recordMetadataDcId;
    @Column(name = "row_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowAdded;
    @Column(name = "row_udpated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowUdpated;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_title", length = 65535)
    private String dcTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_creator", length = 65535)
    private String dcCreator;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_subject", length = 65535)
    private String dcSubject;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_description", length = 65535)
    private String dcDescription;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_publisher", length = 65535)
    private String dcPublisher;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_contributor", length = 65535)
    private String dcContributor;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_date", length = 65535)
    private String dcDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_type", length = 65535)
    private String dcType;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_format", length = 65535)
    private String dcFormat;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_identifier", length = 65535)
    private String dcIdentifier;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_source", length = 65535)
    private String dcSource;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_language", length = 65535)
    private String dcLanguage;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_relation", length = 65535)
    private String dcRelation;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_coverage", length = 65535)
    private String dcCoverage;
    @Lob
    @Size(max = 65535)
    @Column(name = "dc_rights", length = 65535)
    private String dcRights;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRecord recordId;

    public HarRecordMetadataDc() {
    }

    public HarRecordMetadataDc(Long recordMetadataDcId) {
        this.recordMetadataDcId = recordMetadataDcId;
    }

    public Long getRecordMetadataDcId() {
        return recordMetadataDcId;
    }

    public void setRecordMetadataDcId(Long recordMetadataDcId) {
        this.recordMetadataDcId = recordMetadataDcId;
    }

    public Date getRowAdded() {
        return rowAdded;
    }

    public void setRowAdded(Date rowAdded) {
        this.rowAdded = rowAdded;
    }

    public Date getRowUdpated() {
        return rowUdpated;
    }

    public void setRowUdpated(Date rowUdpated) {
        this.rowUdpated = rowUdpated;
    }

    public String getDcTitle() {
        return dcTitle;
    }

    public void setDcTitle(String dcTitle) {
        this.dcTitle = dcTitle;
    }

    public String getDcCreator() {
        return dcCreator;
    }

    public void setDcCreator(String dcCreator) {
        this.dcCreator = dcCreator;
    }

    public String getDcSubject() {
        return dcSubject;
    }

    public void setDcSubject(String dcSubject) {
        this.dcSubject = dcSubject;
    }

    public String getDcDescription() {
        return dcDescription;
    }

    public void setDcDescription(String dcDescription) {
        this.dcDescription = dcDescription;
    }

    public String getDcPublisher() {
        return dcPublisher;
    }

    public void setDcPublisher(String dcPublisher) {
        this.dcPublisher = dcPublisher;
    }

    public String getDcContributor() {
        return dcContributor;
    }

    public void setDcContributor(String dcContributor) {
        this.dcContributor = dcContributor;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    public String getDcFormat() {
        return dcFormat;
    }

    public void setDcFormat(String dcFormat) {
        this.dcFormat = dcFormat;
    }

    public String getDcIdentifier() {
        return dcIdentifier;
    }

    public void setDcIdentifier(String dcIdentifier) {
        this.dcIdentifier = dcIdentifier;
    }

    public String getDcSource() {
        return dcSource;
    }

    public void setDcSource(String dcSource) {
        this.dcSource = dcSource;
    }

    public String getDcLanguage() {
        return dcLanguage;
    }

    public void setDcLanguage(String dcLanguage) {
        this.dcLanguage = dcLanguage;
    }

    public String getDcRelation() {
        return dcRelation;
    }

    public void setDcRelation(String dcRelation) {
        this.dcRelation = dcRelation;
    }

    public String getDcCoverage() {
        return dcCoverage;
    }

    public void setDcCoverage(String dcCoverage) {
        this.dcCoverage = dcCoverage;
    }

    public String getDcRights() {
        return dcRights;
    }

    public void setDcRights(String dcRights) {
        this.dcRights = dcRights;
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
    
}
