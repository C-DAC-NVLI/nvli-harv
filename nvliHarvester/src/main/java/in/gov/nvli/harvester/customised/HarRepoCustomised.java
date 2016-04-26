/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.customised;

import in.gov.nvli.harvester.beans.HarRepoStatus;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author svootla
 */
@XmlRootElement
public class HarRepoCustomised implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer repoId;
    
    private String repoName;

    private String repoBaseUrl;

    private String repoProtocolVersion;

    private Date repoEarliestTimestamp;

    private String repoGranularityDate;

    private String repoDeletionMode;

    private String repoEmail;

    private String repoDesc;

    private String repoCompression;

    private Date repoRegistrationDate;

    private String repoLink;

    private String repoSiteUrl;

    private String repoPermanentLink;

    private byte[] repoLogo;

    private Date repoLastSyncDate;

    private Date repoActivationDate;

    private Date repoRowUpdateTime;

    private String repoLatitude;

    private String repoLongitude;


//
//    private Collection<HarRepoDetail> harRepoDetailCollection;
//
//    private Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection;

//    private Collection<HarRepoLanguage> harRepoLanguageCollection;

//
  //  private HarRepoStatus repoStatusId;
//
//    private HarRepoType repoTypeId;
//
//    private Collection<HarSetRepository> harSetRepositoryCollection;

    public HarRepoCustomised() {
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

}
