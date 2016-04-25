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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarLog.findAll", query = "SELECT h FROM HarLog h"),
    @NamedQuery(name = "HarLog.findByLogId", query = "SELECT h FROM HarLog h WHERE h.logId = :logId"),
    @NamedQuery(name = "HarLog.findByLogStartTime", query = "SELECT h FROM HarLog h WHERE h.logStartTime = :logStartTime"),
    @NamedQuery(name = "HarLog.findByLogEndTime", query = "SELECT h FROM HarLog h WHERE h.logEndTime = :logEndTime")})
public class HarLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id", nullable = false)
    private Long logId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "log_start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "log_end_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logEndTime;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", nullable = false)
    @ManyToOne(optional = false)
    private HarTask taskId;

    public HarLog() {
    }

    public HarLog(Long logId) {
        this.logId = logId;
    }

    public HarLog(Long logId, Date logStartTime, Date logEndTime) {
        this.logId = logId;
        this.logStartTime = logStartTime;
        this.logEndTime = logEndTime;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getLogStartTime() {
        return logStartTime;
    }

    public void setLogStartTime(Date logStartTime) {
        this.logStartTime = logStartTime;
    }

    public Date getLogEndTime() {
        return logEndTime;
    }

    public void setLogEndTime(Date logEndTime) {
        this.logEndTime = logEndTime;
    }

    public HarRepo getRepoId() {
        return repoId;
    }

    public void setRepoId(HarRepo repoId) {
        this.repoId = repoId;
    }

    public HarTask getTaskId() {
        return taskId;
    }

    public void setTaskId(HarTask taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarLog)) {
            return false;
        }
        HarLog other = (HarLog) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarLog[ logId=" + logId + " ]";
    }
    
}
