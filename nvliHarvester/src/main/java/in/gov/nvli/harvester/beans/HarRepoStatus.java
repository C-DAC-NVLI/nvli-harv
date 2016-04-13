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
@Table(name = "har_repo_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoStatus.findAll", query = "SELECT h FROM HarRepoStatus h"),
    @NamedQuery(name = "HarRepoStatus.findByRepoStatusId", query = "SELECT h FROM HarRepoStatus h WHERE h.repoStatusId = :repoStatusId"),
    @NamedQuery(name = "HarRepoStatus.findByRepoStatusName", query = "SELECT h FROM HarRepoStatus h WHERE h.repoStatusName = :repoStatusName")})
public class HarRepoStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_status_id")
    private Short repoStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_status_name")
    private String repoStatusName;
    @Lob
    @Size(max = 65535)
    @Column(name = "repo_status_desc")
    private String repoStatusDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoStatusId")
    private Collection<HarRepo> harRepoCollection;

    public HarRepoStatus() {
    }

    public HarRepoStatus(Short repoStatusId) {
        this.repoStatusId = repoStatusId;
    }

    public HarRepoStatus(Short repoStatusId, String repoStatusName) {
        this.repoStatusId = repoStatusId;
        this.repoStatusName = repoStatusName;
    }

    public Short getRepoStatusId() {
        return repoStatusId;
    }

    public void setRepoStatusId(Short repoStatusId) {
        this.repoStatusId = repoStatusId;
    }

    public String getRepoStatusName() {
        return repoStatusName;
    }

    public void setRepoStatusName(String repoStatusName) {
        this.repoStatusName = repoStatusName;
    }

    public String getRepoStatusDesc() {
        return repoStatusDesc;
    }

    public void setRepoStatusDesc(String repoStatusDesc) {
        this.repoStatusDesc = repoStatusDesc;
    }

    @XmlTransient
    public Collection<HarRepo> getHarRepoCollection() {
        return harRepoCollection;
    }

    public void setHarRepoCollection(Collection<HarRepo> harRepoCollection) {
        this.harRepoCollection = harRepoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repoStatusId != null ? repoStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoStatus)) {
            return false;
        }
        HarRepoStatus other = (HarRepoStatus) object;
        if ((this.repoStatusId == null && other.repoStatusId != null) || (this.repoStatusId != null && !this.repoStatusId.equals(other.repoStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoStatus[ repoStatusId=" + repoStatusId + " ]";
    }
    
}
