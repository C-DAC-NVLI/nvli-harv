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
@Table(name = "har_metadata_type_repository")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarMetadataTypeRepository.findAll", query = "SELECT h FROM HarMetadataTypeRepository h"),
    @NamedQuery(name = "HarMetadataTypeRepository.findByMetadataTypeRepoId", query = "SELECT h FROM HarMetadataTypeRepository h WHERE h.metadataTypeRepoId = :metadataTypeRepoId")})
public class HarMetadataTypeRepository implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "metadata_type_repo_id", nullable = false)
    private Integer metadataTypeRepoId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;
    @JoinColumn(name = "metadata_type_id", referencedColumnName = "metadata_id", nullable = false)
    @ManyToOne(optional = false)
    private HarMetadataType metadataTypeId;

    public HarMetadataTypeRepository() {
    }

    public HarMetadataTypeRepository(Integer metadataTypeRepoId) {
        this.metadataTypeRepoId = metadataTypeRepoId;
    }

    public Integer getMetadataTypeRepoId() {
        return metadataTypeRepoId;
    }

    public void setMetadataTypeRepoId(Integer metadataTypeRepoId) {
        this.metadataTypeRepoId = metadataTypeRepoId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metadataTypeRepoId != null ? metadataTypeRepoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarMetadataTypeRepository)) {
            return false;
        }
        HarMetadataTypeRepository other = (HarMetadataTypeRepository) object;
        if ((this.metadataTypeRepoId == null && other.metadataTypeRepoId != null) || (this.metadataTypeRepoId != null && !this.metadataTypeRepoId.equals(other.metadataTypeRepoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarMetadataTypeRepository[ metadataTypeRepoId=" + metadataTypeRepoId + " ]";
    }
    
}
