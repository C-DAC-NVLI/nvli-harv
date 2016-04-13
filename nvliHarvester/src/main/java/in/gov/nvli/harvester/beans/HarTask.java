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
@Table(name = "har_task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarTask.findAll", query = "SELECT h FROM HarTask h"),
    @NamedQuery(name = "HarTask.findByTaskId", query = "SELECT h FROM HarTask h WHERE h.taskId = :taskId"),
    @NamedQuery(name = "HarTask.findByTaskStartTime", query = "SELECT h FROM HarTask h WHERE h.taskStartTime = :taskStartTime"),
    @NamedQuery(name = "HarTask.findByTaskStatus", query = "SELECT h FROM HarTask h WHERE h.taskStatus = :taskStatus"),
    @NamedQuery(name = "HarTask.findByTaskEndTime", query = "SELECT h FROM HarTask h WHERE h.taskEndTime = :taskEndTime"),
    @NamedQuery(name = "HarTask.findByTaskPriority", query = "SELECT h FROM HarTask h WHERE h.taskPriority = :taskPriority"),
    @NamedQuery(name = "HarTask.findByTaskError", query = "SELECT h FROM HarTask h WHERE h.taskError = :taskError"),
    @NamedQuery(name = "HarTask.findByTaskType", query = "SELECT h FROM HarTask h WHERE h.taskType = :taskType"),
    @NamedQuery(name = "HarTask.findByUserId", query = "SELECT h FROM HarTask h WHERE h.userId = :userId")})
public class HarTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id")
    private Integer taskId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taskStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_status")
    private Character taskStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taskEndTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_priority")
    private short taskPriority;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "task_error")
    private String taskError;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_type")
    private short taskType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private Collection<HarLog> harLogCollection;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id")
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarTask() {
    }

    public HarTask(Integer taskId) {
        this.taskId = taskId;
    }

    public HarTask(Integer taskId, Date taskStartTime, Character taskStatus, Date taskEndTime, short taskPriority, String taskError, short taskType, int userId) {
        this.taskId = taskId;
        this.taskStartTime = taskStartTime;
        this.taskStatus = taskStatus;
        this.taskEndTime = taskEndTime;
        this.taskPriority = taskPriority;
        this.taskError = taskError;
        this.taskType = taskType;
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Character getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Character taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public short getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(short taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskError() {
        return taskError;
    }

    public void setTaskError(String taskError) {
        this.taskError = taskError;
    }

    public short getTaskType() {
        return taskType;
    }

    public void setTaskType(short taskType) {
        this.taskType = taskType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<HarLog> getHarLogCollection() {
        return harLogCollection;
    }

    public void setHarLogCollection(Collection<HarLog> harLogCollection) {
        this.harLogCollection = harLogCollection;
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
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarTask)) {
            return false;
        }
        HarTask other = (HarTask) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarTask[ taskId=" + taskId + " ]";
    }
    
}
