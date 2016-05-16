/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_record_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRecordData.findAll", query = "SELECT h FROM HarRecordData h"),
    @NamedQuery(name = "HarRecordData.findByRecordDataId", query = "SELECT h FROM HarRecordData h WHERE h.recordDataId = :recordDataId"),
    @NamedQuery(name = "HarRecordData.findByRecordDataLicense", query = "SELECT h FROM HarRecordData h WHERE h.recordDataLicense = :recordDataLicense"),
    @NamedQuery(name = "HarRecordData.findByRecordData", query = "SELECT h FROM HarRecordData h WHERE h.recordData = :recordData"),
    @NamedQuery(name = "HarRecordData.findByRecordDataText", query = "SELECT h FROM HarRecordData h WHERE h.recordDataText = :recordDataText")})
public class HarRecordData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_data_id", nullable = false)
    private Long recordDataId;
    @Size(max = 500)
    @Column(name = "record_data_license", length = 500)
    private String recordDataLicense;
    @Size(max = 500)
    @Column(name = "record_data", length = 500)
    private String recordData;
    @Size(max = 500)
    @Column(name = "record_data_text", length = 500)
    private String recordDataText;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRecord recordId;

    public HarRecordData() {
    }

    public HarRecordData(Long recordDataId) {
        this.recordDataId = recordDataId;
    }

    public Long getRecordDataId() {
        return recordDataId;
    }

    public void setRecordDataId(Long recordDataId) {
        this.recordDataId = recordDataId;
    }

    public String getRecordDataLicense() {
        return recordDataLicense;
    }

    public void setRecordDataLicense(String recordDataLicense) {
        this.recordDataLicense = recordDataLicense;
    }

    public String getRecordData() {
        return recordData;
    }

    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }

    public String getRecordDataText() {
        return recordDataText;
    }

    public void setRecordDataText(String recordDataText) {
        this.recordDataText = recordDataText;
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
        hash += (recordDataId != null ? recordDataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRecordData)) {
            return false;
        }
        HarRecordData other = (HarRecordData) object;
        if ((this.recordDataId == null && other.recordDataId != null) || (this.recordDataId != null && !this.recordDataId.equals(other.recordDataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRecordData[ recordDataId=" + recordDataId + " ]";
    }
    
}
