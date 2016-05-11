/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_repo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepo.findAll", query = "SELECT h FROM HarRepo h"),
    @NamedQuery(name = "HarRepo.findByRepoId", query = "SELECT h FROM HarRepo h WHERE h.repoId = :repoId"),
    @NamedQuery(name = "HarRepo.findByRepoUID", query = "SELECT h FROM HarRepo h WHERE h.repoUID = :repoUID"),
    @NamedQuery(name = "HarRepo.findByRepoName", query = "SELECT h FROM HarRepo h WHERE h.repoName = :repoName"),
    @NamedQuery(name = "HarRepo.findByRepoBaseUrl", query = "SELECT h FROM HarRepo h WHERE h.repoBaseUrl = :repoBaseUrl"),
    @NamedQuery(name = "HarRepo.findByRepoProtocolVersion", query = "SELECT h FROM HarRepo h WHERE h.repoProtocolVersion = :repoProtocolVersion"),
    @NamedQuery(name = "HarRepo.findByRepoEarliestTimestamp", query = "SELECT h FROM HarRepo h WHERE h.repoEarliestTimestamp = :repoEarliestTimestamp"),
    @NamedQuery(name = "HarRepo.findByRepoGranularityDate", query = "SELECT h FROM HarRepo h WHERE h.repoGranularityDate = :repoGranularityDate"),
    @NamedQuery(name = "HarRepo.findByRepoDeletionMode", query = "SELECT h FROM HarRepo h WHERE h.repoDeletionMode = :repoDeletionMode"),
    @NamedQuery(name = "HarRepo.findByRepoEmail", query = "SELECT h FROM HarRepo h WHERE h.repoEmail = :repoEmail"),
    @NamedQuery(name = "HarRepo.findByRepoCompression", query = "SELECT h FROM HarRepo h WHERE h.repoCompression = :repoCompression"),
    @NamedQuery(name = "HarRepo.findByRepoRegistrationDate", query = "SELECT h FROM HarRepo h WHERE h.repoRegistrationDate = :repoRegistrationDate"),
    @NamedQuery(name = "HarRepo.findByRepoLink", query = "SELECT h FROM HarRepo h WHERE h.repoLink = :repoLink"),
    @NamedQuery(name = "HarRepo.findByRepoSiteUrl", query = "SELECT h FROM HarRepo h WHERE h.repoSiteUrl = :repoSiteUrl"),
    @NamedQuery(name = "HarRepo.findByRepoPermanentLink", query = "SELECT h FROM HarRepo h WHERE h.repoPermanentLink = :repoPermanentLink"),
    @NamedQuery(name = "HarRepo.findByRepoLastSyncDate", query = "SELECT h FROM HarRepo h WHERE h.repoLastSyncDate = :repoLastSyncDate"),
    @NamedQuery(name = "HarRepo.findByRepoActivationDate", query = "SELECT h FROM HarRepo h WHERE h.repoActivationDate = :repoActivationDate"),
    @NamedQuery(name = "HarRepo.findByRepoRowUpdateTime", query = "SELECT h FROM HarRepo h WHERE h.repoRowUpdateTime = :repoRowUpdateTime"),
    @NamedQuery(name = "HarRepo.findByRepoLatitude", query = "SELECT h FROM HarRepo h WHERE h.repoLatitude = :repoLatitude"),
    @NamedQuery(name = "HarRepo.findByRepoLongitude", query = "SELECT h FROM HarRepo h WHERE h.repoLongitude = :repoLongitude")})
public class HarRepo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_id", nullable = false)
    private Integer repoId;
    
    @Column(name = "repo_uid", nullable = false)
    @Size(min = 1, max = 200)
    private String repoUID;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "repo_name", nullable = false, length = 500)
    private String repoName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_base_url", nullable = false, length = 255)
    private String repoBaseUrl;
    @Size(max = 255)
    @Column(name = "repo_protocol_version", length = 255)
    private String repoProtocolVersion;
    @Column(name = "repo_earliest_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoEarliestTimestamp;
    @Size(max = 255)
    @Column(name = "repo_granularity_date", length = 255)
    private String repoGranularityDate;
    @Size(max = 255)
    @Column(name = "repo_deletion_mode", length = 255)
    private String repoDeletionMode;
    @Size(max = 500)
    @Column(name = "repo_email", length = 500)
    private String repoEmail;
    @Lob
    @Size(max = 65535)
    @Column(name = "repo_desc", length = 65535)
    private String repoDesc;
    @Size(max = 500)
    @Column(name = "repo_compression", length = 500)
    private String repoCompression;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoRegistrationDate;
    @Size(max = 255)
    @Column(name = "repo_link", length = 255)
    private String repoLink;
    @Size(max = 255)
    @Column(name = "repo_site_url", length = 255)
    private String repoSiteUrl;
    @Size(max = 255)
    @Column(name = "repo_permanent_link", length = 255)
    private String repoPermanentLink;
    @Lob
    @Column(name = "repo_logo")
    private byte[] repoLogo;
    @Column(name = "repo_last_sync_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoLastSyncDate;
    @Column(name = "repo_activation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoActivationDate;
    @Column(name = "repo_row_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoRowUpdateTime;
    @Size(max = 255)
    @Column(name = "repo_latitude", length = 255)
    private String repoLatitude;
    @Size(max = 255)
    @Column(name = "repo_longitude", length = 255)
    private String repoLongitude;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarLog> harLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarRepoDetail> harRepoDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarTask> harTaskCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarRepoGroup> harRepoGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarRepoLanguage> harRepoLanguageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarRecord> harRecordCollection;
    @JoinColumn(name = "repo_status_id", referencedColumnName = "repo_status_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepoStatus repoStatusId;
    @JoinColumn(name = "repo_type_id", referencedColumnName = "repo_type_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepoType repoTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarSetRepository> harSetRepositoryCollection;

    @Column(name = "ore_enable_flag",nullable = false)
    private byte oreEnableFlag=0;//1=enabled,0-disabled/not enabled(default)
    
    public HarRepo() {
    }

    public HarRepo(Integer repoId) {
        this.repoId = repoId;
    }

    public HarRepo(Integer repoId, String repoName, String repoBaseUrl, Date repoRegistrationDate) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.repoBaseUrl = repoBaseUrl;
        this.repoRegistrationDate = repoRegistrationDate;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public String getRepoUID() {
        return repoUID;
    }

    public void setRepoUID(String repoUID) {
        this.repoUID = repoUID;
    }

   

    
    
    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoBaseUrl() {
        return repoBaseUrl;
    }

    public void setRepoBaseUrl(String repoBaseUrl) {
        this.repoBaseUrl = repoBaseUrl;
    }

    public String getRepoProtocolVersion() {
        return repoProtocolVersion;
    }

    public void setRepoProtocolVersion(String repoProtocolVersion) {
        this.repoProtocolVersion = repoProtocolVersion;
    }

    public Date getRepoEarliestTimestamp() {
        return repoEarliestTimestamp;
    }

    public void setRepoEarliestTimestamp(Date repoEarliestTimestamp) {
        this.repoEarliestTimestamp = repoEarliestTimestamp;
    }

    public String getRepoGranularityDate() {
        return repoGranularityDate;
    }

    public void setRepoGranularityDate(String repoGranularityDate) {
        this.repoGranularityDate = repoGranularityDate;
    }

    public String getRepoDeletionMode() {
        return repoDeletionMode;
    }

    public void setRepoDeletionMode(String repoDeletionMode) {
        this.repoDeletionMode = repoDeletionMode;
    }

    public String getRepoEmail() {
        return repoEmail;
    }

    public void setRepoEmail(String repoEmail) {
        this.repoEmail = repoEmail;
    }

    public String getRepoDesc() {
        return repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getRepoCompression() {
        return repoCompression;
    }

    public void setRepoCompression(String repoCompression) {
        this.repoCompression = repoCompression;
    }

    public Date getRepoRegistrationDate() {
        return repoRegistrationDate;
    }

    public void setRepoRegistrationDate(Date repoRegistrationDate) {
        this.repoRegistrationDate = repoRegistrationDate;
    }

    public String getRepoLink() {
        return repoLink;
    }

    public void setRepoLink(String repoLink) {
        this.repoLink = repoLink;
    }

    public String getRepoSiteUrl() {
        return repoSiteUrl;
    }

    public void setRepoSiteUrl(String repoSiteUrl) {
        this.repoSiteUrl = repoSiteUrl;
    }

    public String getRepoPermanentLink() {
        return repoPermanentLink;
    }

    public void setRepoPermanentLink(String repoPermanentLink) {
        this.repoPermanentLink = repoPermanentLink;
    }

    public byte[] getRepoLogo() {
        return repoLogo;
    }

    public void setRepoLogo(byte[] repoLogo) {
        this.repoLogo = repoLogo;
    }

    public Date getRepoLastSyncDate() {
        return repoLastSyncDate;
    }

    public void setRepoLastSyncDate(Date repoLastSyncDate) {
        this.repoLastSyncDate = repoLastSyncDate;
    }

    public Date getRepoActivationDate() {
        return repoActivationDate;
    }

    public void setRepoActivationDate(Date repoActivationDate) {
        this.repoActivationDate = repoActivationDate;
    }

    public Date getRepoRowUpdateTime() {
        return repoRowUpdateTime;
    }

    public void setRepoRowUpdateTime(Date repoRowUpdateTime) {
        this.repoRowUpdateTime = repoRowUpdateTime;
    }

    public String getRepoLatitude() {
        return repoLatitude;
    }

    public void setRepoLatitude(String repoLatitude) {
        this.repoLatitude = repoLatitude;
    }

    public String getRepoLongitude() {
        return repoLongitude;
    }

    public void setRepoLongitude(String repoLongitude) {
        this.repoLongitude = repoLongitude;
    }

    @XmlTransient
    public Collection<HarLog> getHarLogCollection() {
        return harLogCollection;
    }

    public void setHarLogCollection(Collection<HarLog> harLogCollection) {
        this.harLogCollection = harLogCollection;
    }

    @XmlTransient
    public Collection<HarRepoDetail> getHarRepoDetailCollection() {
        return harRepoDetailCollection;
    }

    public void setHarRepoDetailCollection(Collection<HarRepoDetail> harRepoDetailCollection) {
        this.harRepoDetailCollection = harRepoDetailCollection;
    }

    @XmlTransient
    public Collection<HarMetadataTypeRepository> getHarMetadataTypeRepositoryCollection() {
        return harMetadataTypeRepositoryCollection;
    }

    public void setHarMetadataTypeRepositoryCollection(Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection) {
        this.harMetadataTypeRepositoryCollection = harMetadataTypeRepositoryCollection;
    }

    @XmlTransient
    public Collection<HarTask> getHarTaskCollection() {
        return harTaskCollection;
    }

    public void setHarTaskCollection(Collection<HarTask> harTaskCollection) {
        this.harTaskCollection = harTaskCollection;
    }

    @XmlTransient
    public Collection<HarRepoGroup> getHarRepoGroupCollection() {
        return harRepoGroupCollection;
    }

    public void setHarRepoGroupCollection(Collection<HarRepoGroup> harRepoGroupCollection) {
        this.harRepoGroupCollection = harRepoGroupCollection;
    }

    @XmlTransient
    public Collection<HarRepoLanguage> getHarRepoLanguageCollection() {
        return harRepoLanguageCollection;
    }

    public void setHarRepoLanguageCollection(Collection<HarRepoLanguage> harRepoLanguageCollection) {
        this.harRepoLanguageCollection = harRepoLanguageCollection;
    }

    @XmlTransient
    public Collection<HarRecord> getHarRecordCollection() {
        return harRecordCollection;
    }

    public void setHarRecordCollection(Collection<HarRecord> harRecordCollection) {
        this.harRecordCollection = harRecordCollection;
    }

    public HarRepoStatus getRepoStatusId() {
        return repoStatusId;
    }

    public void setRepoStatusId(HarRepoStatus repoStatusId) {
        this.repoStatusId = repoStatusId;
    }

    public HarRepoType getRepoTypeId() {
        return repoTypeId;
    }

    public void setRepoTypeId(HarRepoType repoTypeId) {
        this.repoTypeId = repoTypeId;
    }

    @XmlTransient
    public Collection<HarSetRepository> getHarSetRepositoryCollection() {
        return harSetRepositoryCollection;
    }

    public void setHarSetRepositoryCollection(Collection<HarSetRepository> harSetRepositoryCollection) {
        this.harSetRepositoryCollection = harSetRepositoryCollection;
    }

    public byte getOreEnableFlag() {
        return oreEnableFlag;
    }

    public void setOreEnableFlag(byte oreEnableFlag) {
        this.oreEnableFlag = oreEnableFlag;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repoId != null ? repoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepo)) {
            return false;
        }
        HarRepo other = (HarRepo) object;
        if ((this.repoId == null && other.repoId != null) || (this.repoId != null && !this.repoId.equals(other.repoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepo[ repoId=" + repoId + " ]";
    }
    
}
