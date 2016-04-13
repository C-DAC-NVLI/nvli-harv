/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_language")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarLanguage.findAll", query = "SELECT h FROM HarLanguage h"),
    @NamedQuery(name = "HarLanguage.findByLanguageId", query = "SELECT h FROM HarLanguage h WHERE h.languageId = :languageId"),
    @NamedQuery(name = "HarLanguage.findByLanguageCode", query = "SELECT h FROM HarLanguage h WHERE h.languageCode = :languageCode")})
public class HarLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "language_id")
    private Short languageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "language_code")
    private String languageCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "language_desc")
    private String languageDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languageId")
    private Collection<HarRecordLanguage> harRecordLanguageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languageId")
    private Collection<HarRepoLanguage> harRepoLanguageCollection;

    public HarLanguage() {
    }

    public HarLanguage(Short languageId) {
        this.languageId = languageId;
    }

    public HarLanguage(Short languageId, String languageCode) {
        this.languageId = languageId;
        this.languageCode = languageCode;
    }

    public Short getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Short languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageDesc() {
        return languageDesc;
    }

    public void setLanguageDesc(String languageDesc) {
        this.languageDesc = languageDesc;
    }

    @XmlTransient
    public Collection<HarRecordLanguage> getHarRecordLanguageCollection() {
        return harRecordLanguageCollection;
    }

    public void setHarRecordLanguageCollection(Collection<HarRecordLanguage> harRecordLanguageCollection) {
        this.harRecordLanguageCollection = harRecordLanguageCollection;
    }

    @XmlTransient
    public Collection<HarRepoLanguage> getHarRepoLanguageCollection() {
        return harRepoLanguageCollection;
    }

    public void setHarRepoLanguageCollection(Collection<HarRepoLanguage> harRepoLanguageCollection) {
        this.harRepoLanguageCollection = harRepoLanguageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (languageId != null ? languageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarLanguage)) {
            return false;
        }
        HarLanguage other = (HarLanguage) object;
        if ((this.languageId == null && other.languageId != null) || (this.languageId != null && !this.languageId.equals(other.languageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarLanguage[ languageId=" + languageId + " ]";
    }
    
}
