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
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "HarRepoDetail.findByRepoDetailEmail", query = "SELECT h FROM HarRepoDetail h WHERE h.repoDetailEmail = :repoDetailEmail"),
    @NamedQuery(name = "HarRepoDetail.findByRepoDetailCompression", query = "SELECT h FROM HarRepoDetail h WHERE h.repoDetailCompression = :repoDetailCompression")})
public class HarRepoDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_detail_id")
    private Integer repoDetailId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_detail_email")
    private String repoDetailEmail;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "repo_detail_desc")
    private String repoDetailDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_detail_compression")
    private String repoDetailCompression;
    @JoinColumn(name = "repo_classification_id", referencedColumnName = "classification_id")
    @ManyToOne(optional = false)
    private HarClassification repoClassificationId;
    @JoinColumn(name = "repo_type_id", referencedColumnName = "repo_type_id")
    @ManyToOne(optional = false)
    private HarRepoType repoTypeId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id")
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarRepoDetail() {
    }

    public HarRepoDetail(Integer repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    public HarRepoDetail(Integer repoDetailId, String repoDetailEmail, String repoDetailDesc, String repoDetailCompression) {
        this.repoDetailId = repoDetailId;
        this.repoDetailEmail = repoDetailEmail;
        this.repoDetailDesc = repoDetailDesc;
        this.repoDetailCompression = repoDetailCompression;
    }

    public Integer getRepoDetailId() {
        return repoDetailId;
    }

    public void setRepoDetailId(Integer repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    public String getRepoDetailEmail() {
        return repoDetailEmail;
    }

    public void setRepoDetailEmail(String repoDetailEmail) {
        this.repoDetailEmail = repoDetailEmail;
    }

    public String getRepoDetailDesc() {
        return repoDetailDesc;
    }

    public void setRepoDetailDesc(String repoDetailDesc) {
        this.repoDetailDesc = repoDetailDesc;
    }

    public String getRepoDetailCompression() {
        return repoDetailCompression;
    }

    public void setRepoDetailCompression(String repoDetailCompression) {
        this.repoDetailCompression = repoDetailCompression;
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
