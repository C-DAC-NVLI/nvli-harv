/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_repo_metadata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoMetadata.findAll", query = "SELECT h FROM HarRepoMetadata h"),
    @NamedQuery(name = "HarRepoMetadata.findByRepoMetadataId", query = "SELECT h FROM HarRepoMetadata h WHERE h.repoMetadataId = :repoMetadataId"),
    @NamedQuery(name = "HarRepoMetadata.findByHarvestEndTime", query = "SELECT h FROM HarRepoMetadata h WHERE h.harvestEndTime = :harvestEndTime"),
    @NamedQuery(name = "HarRepoMetadata.findByHarvestStartTime", query = "SELECT h FROM HarRepoMetadata h WHERE h.harvestStartTime = :harvestStartTime"),
    @NamedQuery(name = "HarRepoMetadata.findByResumptionTokenListRecords", query = "SELECT h FROM HarRepoMetadata h WHERE h.resumptionTokenListRecords = :resumptionTokenListRecords")})
public class HarRepoMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_metadata_id", nullable = false)
    private Integer repoMetadataId;
    @Column(name = "harvest_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date harvestEndTime;
    @Column(name = "harvest_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date harvestStartTime;
    @Size(max = 255)
    @Column(name = "resumption_token_list_records", length = 255)
    private String resumptionTokenListRecords;
    @JoinColumn(name = "harvest_status", referencedColumnName = "repo_status_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepoStatus harvestStatus;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;
    @JoinColumn(name = "metadata_type_id", referencedColumnName = "metadata_id", nullable = false)
    @ManyToOne(optional = false)
    private HarMetadataType metadataTypeId;

    public HarRepoMetadata() {
    }

    public HarRepoMetadata(Integer repoMetadataId) {
        this.repoMetadataId = repoMetadataId;
    }

    public Integer getRepoMetadataId() {
        return repoMetadataId;
    }

    public void setRepoMetadataId(Integer repoMetadataId) {
        this.repoMetadataId = repoMetadataId;
    }

    public Date getHarvestEndTime() {
        return harvestEndTime;
    }

    public void setHarvestEndTime(Date harvestEndTime) {
        this.harvestEndTime = harvestEndTime;
    }

    public Date getHarvestStartTime() {
        return harvestStartTime;
    }

    public void setHarvestStartTime(Date harvestStartTime) {
        this.harvestStartTime = harvestStartTime;
    }

    public String getResumptionTokenListRecords() {
        return resumptionTokenListRecords;
    }

    public void setResumptionTokenListRecords(String resumptionTokenListRecords) {
        this.resumptionTokenListRecords = resumptionTokenListRecords;
    }

    public HarRepoStatus getHarvestStatus() {
        return harvestStatus;
    }

    public void setHarvestStatus(HarRepoStatus harvestStatus) {
        this.harvestStatus = harvestStatus;
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
        hash += (repoMetadataId != null ? repoMetadataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoMetadata)) {
            return false;
        }
        HarRepoMetadata other = (HarRepoMetadata) object;
        if ((this.repoMetadataId == null && other.repoMetadataId != null) || (this.repoMetadataId != null && !this.repoMetadataId.equals(other.repoMetadataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoMetadata[ repoMetadataId=" + repoMetadataId + " ]";
    }

}
