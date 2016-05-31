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
@Table(name = "har_repo_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRepoGroup.findAll", query = "SELECT h FROM HarRepoGroup h"),
    @NamedQuery(name = "HarRepoGroup.findByRepoGroupId", query = "SELECT h FROM HarRepoGroup h WHERE h.repoGroupId = :repoGroupId")})
public class HarRepoGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "repo_group_id", nullable = false)
    private Integer repoGroupId;
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    @ManyToOne(optional = false)
    private HarGroup groupId;
    @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRepo repoId;

    public HarRepoGroup() {
    }

    public HarRepoGroup(Integer repoGroupId) {
        this.repoGroupId = repoGroupId;
    }

    public Integer getRepoGroupId() {
        return repoGroupId;
    }

    public void setRepoGroupId(Integer repoGroupId) {
        this.repoGroupId = repoGroupId;
    }

    public HarGroup getGroupId() {
        return groupId;
    }

    public void setGroupId(HarGroup groupId) {
        this.groupId = groupId;
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
        hash += (repoGroupId != null ? repoGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRepoGroup)) {
            return false;
        }
        HarRepoGroup other = (HarRepoGroup) object;
        if ((this.repoGroupId == null && other.repoGroupId != null) || (this.repoGroupId != null && !this.repoGroupId.equals(other.repoGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRepoGroup[ repoGroupId=" + repoGroupId + " ]";
    }
    
}
