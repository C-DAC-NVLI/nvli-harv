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
@Table(name = "har_repo_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoType.findAll", query = "SELECT h FROM HarRepoType h"),
    @NamedQuery(name = "HarRepoType.findByRepoTypeId", query = "SELECT h FROM HarRepoType h WHERE h.repoTypeId = :repoTypeId"),
    @NamedQuery(name = "HarRepoType.findByRepoTypeName", query = "SELECT h FROM HarRepoType h WHERE h.repoTypeName = :repoTypeName")})
public class HarRepoType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_type_id", nullable = false)
    private Integer repoTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "repo_type_name", nullable = false, length = 500)
    private String repoTypeName;
    @Lob
    @Size(max = 65535)
    @Column(name = "repo_type_desc", length = 65535)
    private String repoTypeDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoTypeId")
    private Collection<HarRepoDetail> harRepoDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoTypeId")
    private Collection<HarRepo> harRepoCollection;

    public HarRepoType() {
    }

    public HarRepoType(Integer repoTypeId) {
        this.repoTypeId = repoTypeId;
    }

    public HarRepoType(Integer repoTypeId, String repoTypeName) {
        this.repoTypeId = repoTypeId;
        this.repoTypeName = repoTypeName;
    }

    public Integer getRepoTypeId() {
        return repoTypeId;
    }

    public void setRepoTypeId(Integer repoTypeId) {
        this.repoTypeId = repoTypeId;
    }

    public String getRepoTypeName() {
        return repoTypeName;
    }

    public void setRepoTypeName(String repoTypeName) {
        this.repoTypeName = repoTypeName;
    }

    public String getRepoTypeDesc() {
        return repoTypeDesc;
    }

    public void setRepoTypeDesc(String repoTypeDesc) {
        this.repoTypeDesc = repoTypeDesc;
    }

    @XmlTransient
    public Collection<HarRepoDetail> getHarRepoDetailCollection() {
        return harRepoDetailCollection;
    }

    public void setHarRepoDetailCollection(Collection<HarRepoDetail> harRepoDetailCollection) {
        this.harRepoDetailCollection = harRepoDetailCollection;
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
        hash += (repoTypeId != null ? repoTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoType)) {
            return false;
        }
        HarRepoType other = (HarRepoType) object;
        if ((this.repoTypeId == null && other.repoTypeId != null) || (this.repoTypeId != null && !this.repoTypeId.equals(other.repoTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoType[ repoTypeId=" + repoTypeId + " ]";
    }
    
}
