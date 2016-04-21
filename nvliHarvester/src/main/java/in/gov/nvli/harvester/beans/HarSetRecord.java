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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_set_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarSetRecord.findAll", query = "SELECT h FROM HarSetRecord h"),
    @NamedQuery(name = "HarSetRecord.findBySetRecordId", query = "SELECT h FROM HarSetRecord h WHERE h.setRecordId = :setRecordId")})
public class HarSetRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "set_record_id")
    private Long setRecordId;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id")
    @ManyToOne(optional = false)
    private HarRecord recordId;
    @JoinColumn(name = "set_id", referencedColumnName = "set_id")
    @ManyToOne(optional = false)
    private HarSet setId;

    public HarSetRecord() {
    }

    public HarSetRecord(Long setRecordId) {
        this.setRecordId = setRecordId;
    }

    public Long getSetRecordId() {
        return setRecordId;
    }

    public void setSetRecordId(Long setRecordId) {
        this.setRecordId = setRecordId;
    }

    public HarRecord getRecordId() {
        return recordId;
    }

    public void setRecordId(HarRecord recordId) {
        this.recordId = recordId;
    }

    public HarSet getSetId() {
        return setId;
    }

    public void setSetId(HarSet setId) {
        this.setId = setId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (setRecordId != null ? setRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarSetRecord)) {
            return false;
        }
        HarSetRecord other = (HarSetRecord) object;
        if ((this.setRecordId == null && other.setRecordId != null) || (this.setRecordId != null && !this.setRecordId.equals(other.setRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarSetRecord[ setRecordId=" + setRecordId + " ]";
    }
    
}
