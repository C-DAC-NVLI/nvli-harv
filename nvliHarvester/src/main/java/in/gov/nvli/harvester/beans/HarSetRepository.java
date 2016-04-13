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
@Table(name = "har_set_repository")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarSetRepository.findAll", query = "SELECT h FROM HarSetRepository h"),
    @NamedQuery(name = "HarSetRepository.findBySetRepositoryId", query = "SELECT h FROM HarSetRepository h WHERE h.setRepositoryId = :setRepositoryId")})
public class HarSetRepository implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "set_repository_id")
    private Long setRepositoryId;
    @JoinColumn(name = "set_id", referencedColumnName = "set_id")
    @ManyToOne(optional = false)
    private HarSet setId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id")
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarSetRepository() {
    }

    public HarSetRepository(Long setRepositoryId) {
        this.setRepositoryId = setRepositoryId;
    }

    public Long getSetRepositoryId() {
        return setRepositoryId;
    }

    public void setSetRepositoryId(Long setRepositoryId) {
        this.setRepositoryId = setRepositoryId;
    }

    public HarSet getSetId() {
        return setId;
    }

    public void setSetId(HarSet setId) {
        this.setId = setId;
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
        hash += (setRepositoryId != null ? setRepositoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarSetRepository)) {
            return false;
        }
        HarSetRepository other = (HarSetRepository) object;
        if ((this.setRepositoryId == null && other.setRepositoryId != null) || (this.setRepositoryId != null && !this.setRepositoryId.equals(other.setRepositoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarSetRepository[ setRepositoryId=" + setRepositoryId + " ]";
    }
    
}
