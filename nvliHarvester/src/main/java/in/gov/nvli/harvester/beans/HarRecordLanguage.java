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
@Table(name = "har_record_language")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRecordLanguage.findAll", query = "SELECT h FROM HarRecordLanguage h"),
    @NamedQuery(name = "HarRecordLanguage.findByRecordLanguageId", query = "SELECT h FROM HarRecordLanguage h WHERE h.recordLanguageId = :recordLanguageId")})
public class HarRecordLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_language_id", nullable = false)
    private Long recordLanguageId;
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @ManyToOne(optional = false)
    private HarLanguage languageId;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRecord recordId;

    public HarRecordLanguage() {
    }

    public HarRecordLanguage(Long recordLanguageId) {
        this.recordLanguageId = recordLanguageId;
    }

    public Long getRecordLanguageId() {
        return recordLanguageId;
    }

    public void setRecordLanguageId(Long recordLanguageId) {
        this.recordLanguageId = recordLanguageId;
    }

    public HarLanguage getLanguageId() {
        return languageId;
    }

    public void setLanguageId(HarLanguage languageId) {
        this.languageId = languageId;
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
        hash += (recordLanguageId != null ? recordLanguageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRecordLanguage)) {
            return false;
        }
        HarRecordLanguage other = (HarRecordLanguage) object;
        if ((this.recordLanguageId == null && other.recordLanguageId != null) || (this.recordLanguageId != null && !this.recordLanguageId.equals(other.recordLanguageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRecordLanguage[ recordLanguageId=" + recordLanguageId + " ]";
    }
    
}
