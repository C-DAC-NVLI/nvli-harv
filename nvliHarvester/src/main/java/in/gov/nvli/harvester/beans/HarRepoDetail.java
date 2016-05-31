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
import javax.persistence.Lob;
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
@Table(name = "har_repo_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoDetail.findAll", query = "SELECT h FROM HarRepoDetail h"),
    @NamedQuery(name = "HarRepoDetail.findByRepoDetailId", query = "SELECT h FROM HarRepoDetail h WHERE h.repoDetailId = :repoDetailId"),
    @NamedQuery(name = "HarRepoDetail.findByRepoDetailCompression", query = "SELECT h FROM HarRepoDetail h WHERE h.repoDetailCompression = :repoDetailCompression"),
    @NamedQuery(name = "HarRepoDetail.findByRepoDetailEmail", query = "SELECT h FROM HarRepoDetail h WHERE h.repoDetailEmail = :repoDetailEmail")})
public class HarRepoDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_detail_id", nullable = false)
    private Integer repoDetailId;
    @Size(max = 500)
    @Column(name = "repo_detail_compression", length = 500)
    private String repoDetailCompression;
    @Lob
    @Size(max = 65535)
    @Column(name = "repo_detail_desc", length = 65535)
    private String repoDetailDesc;
    @Size(max = 500)
    @Column(name = "repo_detail_email", length = 500)
    private String repoDetailEmail;
    @JoinColumn(name = "repo_classification_id", referencedColumnName = "classification_id", nullable = false)
    @ManyToOne(optional = false)
    private HarClassification repoClassificationId;
    @JoinColumn(name = "repo_type_id", referencedColumnName = "repo_type_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepoType repoTypeId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarRepoDetail() {
    }

    public HarRepoDetail(Integer repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    public Integer getRepoDetailId() {
        return repoDetailId;
    }

    public void setRepoDetailId(Integer repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    public String getRepoDetailCompression() {
        return repoDetailCompression;
    }

    public void setRepoDetailCompression(String repoDetailCompression) {
        this.repoDetailCompression = repoDetailCompression;
    }

    public String getRepoDetailDesc() {
        return repoDetailDesc;
    }

    public void setRepoDetailDesc(String repoDetailDesc) {
        this.repoDetailDesc = repoDetailDesc;
    }

    public String getRepoDetailEmail() {
        return repoDetailEmail;
    }

    public void setRepoDetailEmail(String repoDetailEmail) {
        this.repoDetailEmail = repoDetailEmail;
    }

    public HarClassification getRepoClassificationId() {
        return repoClassificationId;
    }

    public void setRepoClassificationId(HarClassification repoClassificationId) {
        this.repoClassificationId = repoClassificationId;
    }

    public HarRepoType getRepoTypeId() {
        return repoTypeId;
    }

    public void setRepoTypeId(HarRepoType repoTypeId) {
        this.repoTypeId = repoTypeId;
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
        hash += (repoDetailId != null ? repoDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoDetail)) {
            return false;
        }
        HarRepoDetail other = (HarRepoDetail) object;
        if ((this.repoDetailId == null && other.repoDetailId != null) || (this.repoDetailId != null && !this.repoDetailId.equals(other.repoDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoDetail[ repoDetailId=" + repoDetailId + " ]";
    }
    
}
