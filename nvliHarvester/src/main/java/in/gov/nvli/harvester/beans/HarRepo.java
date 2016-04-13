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
    @NamedQuery(name = "HarRepo.findByRepoName", query = "SELECT h FROM HarRepo h WHERE h.repoName = :repoName"),
    @NamedQuery(name = "HarRepo.findByRepoBaseUrl", query = "SELECT h FROM HarRepo h WHERE h.repoBaseUrl = :repoBaseUrl"),
    @NamedQuery(name = "HarRepo.findByRepoProtocolVersion", query = "SELECT h FROM HarRepo h WHERE h.repoProtocolVersion = :repoProtocolVersion"),
    @NamedQuery(name = "HarRepo.findByRepoEarliestTimestamp", query = "SELECT h FROM HarRepo h WHERE h.repoEarliestTimestamp = :repoEarliestTimestamp"),
    @NamedQuery(name = "HarRepo.findByRepoGranularityDate", query = "SELECT h FROM HarRepo h WHERE h.repoGranularityDate = :repoGranularityDate"),
    @NamedQuery(name = "HarRepo.findByRepoDeletionMode", query = "SELECT h FROM HarRepo h WHERE h.repoDeletionMode = :repoDeletionMode"),
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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_email")
    private String repoEmail;
    @Lob
    @Size(max = 65535)
    @Column(name = "repol_desc")
    private String repolDesc;
    @Size(max = 500)
    @Column(name = "repo_compression")
    private String repoCompression;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "repo_logo")
    private byte[] repoLogo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_id")
    private Integer repoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "repo_name")
    private String repoName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_base_url")
    private String repoBaseUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_protocol_version")
    private String repoProtocolVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_earliest_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoEarliestTimestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_granularity_date")
    private String repoGranularityDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_deletion_mode")
    private String repoDeletionMode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoRegistrationDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_link")
    private String repoLink;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_site_url")
    private String repoSiteUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_permanent_link")
    private String repoPermanentLink;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_last_sync_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoLastSyncDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_activation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoActivationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "repo_row_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repoRowUpdateTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_latitude")
    private String repoLatitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "repo_longitude")
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
    @JoinColumn(name = "repo_status_id", referencedColumnName = "repo_status_id")
    @ManyToOne(optional = false)
    private HarRepoStatus repoStatusId;
    @JoinColumn(name = "repo_type_id", referencedColumnName = "repo_type_id")
    @ManyToOne(optional = false)
    private HarRepoType repoTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarSetRepository> harSetRepositoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoId")
    private Collection<HarSetRecord> harSetRecordCollection;

    public HarRepo() {
    }

    public HarRepo(Integer repoId) {
        this.repoId = repoId;
    }

    public HarRepo(Integer repoId, String repoName, String repoBaseUrl, String repoProtocolVersion, Date repoEarliestTimestamp, String repoGranularityDate, String repoDeletionMode, Date repoRegistrationDate, String repoLink, String repoSiteUrl, String repoPermanentLink, byte[] repoLogo, Date repoLastSyncDate, Date repoActivationDate, Date repoRowUpdateTime, String repoLatitude, String repoLongitude) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.repoBaseUrl = repoBaseUrl;
        this.repoProtocolVersion = repoProtocolVersion;
        this.repoEarliestTimestamp = repoEarliestTimestamp;
        this.repoGranularityDate = repoGranularityDate;
        this.repoDeletionMode = repoDeletionMode;
        this.repoRegistrationDate = repoRegistrationDate;
        this.repoLink = repoLink;
        this.repoSiteUrl = repoSiteUrl;
        this.repoPermanentLink = repoPermanentLink;
        this.repoLogo = repoLogo;
        this.repoLastSyncDate = repoLastSyncDate;
        this.repoActivationDate = repoActivationDate;
        this.repoRowUpdateTime = repoRowUpdateTime;
        this.repoLatitude = repoLatitude;
        this.repoLongitude = repoLongitude;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
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

    @XmlTransient
    public Collection<HarSetRecord> getHarSetRecordCollection() {
        return harSetRecordCollection;
    }

    public void setHarSetRecordCollection(Collection<HarSetRecord> harSetRecordCollection) {
        this.harSetRecordCollection = harSetRecordCollection;
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

    public String getRepoEmail() {
        return repoEmail;
    }

    public void setRepoEmail(String repoEmail) {
        this.repoEmail = repoEmail;
    }

    public String getRepolDesc() {
        return repolDesc;
    }

    public void setRepolDesc(String repolDesc) {
        this.repolDesc = repolDesc;
    }

    public String getRepoCompression() {
        return repoCompression;
    }

    public void setRepoCompression(String repoCompression) {
        this.repoCompression = repoCompression;
    }    
}
