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
@Table(name = "har_repo_language")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoLanguage.findAll", query = "SELECT h FROM HarRepoLanguage h"),
    @NamedQuery(name = "HarRepoLanguage.findByRepoLanguageId", query = "SELECT h FROM HarRepoLanguage h WHERE h.repoLanguageId = :repoLanguageId")})
public class HarRepoLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_language_id", nullable = false)
    private Integer repoLanguageId;
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @ManyToOne(optional = false)
    private HarLanguage languageId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarRepoLanguage() {
    }

    public HarRepoLanguage(Integer repoLanguageId) {
        this.repoLanguageId = repoLanguageId;
    }

    public Integer getRepoLanguageId() {
        return repoLanguageId;
    }

    public void setRepoLanguageId(Integer repoLanguageId) {
        this.repoLanguageId = repoLanguageId;
    }

    public HarLanguage getLanguageId() {
        return languageId;
    }

    public void setLanguageId(HarLanguage languageId) {
        this.languageId = languageId;
    }

    public HarRepo getRepoId() {
        return repoId;
    }

    public void setRepoId(HarRepo repoId) {
        this.repoId = repoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repoLanguageId != null ? repoLanguageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoLanguage)) {
            return false;
        }
        HarRepoLanguage other = (HarRepoLanguage) object;
        if ((this.repoLanguageId == null && other.repoLanguageId != null) || (this.repoLanguageId != null && !this.repoLanguageId.equals(other.repoLanguageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoLanguage[ repoLanguageId=" + repoLanguageId + " ]";
    }
    
}
