/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarGroup.findAll", query = "SELECT h FROM HarGroup h"),
    @NamedQuery(name = "HarGroup.findByGroupId", query = "SELECT h FROM HarGroup h WHERE h.groupId = :groupId"),
    @NamedQuery(name = "HarGroup.findByGroupName", query = "SELECT h FROM HarGroup h WHERE h.groupName = :groupName")})
public class HarGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "group_id")
    private Short groupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "group_name")
    private String groupName;
    @Lob
    @Size(max = 65535)
    @Column(name = "group_desc")
    private String groupDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<HarRepoGroup> harRepoGroupCollection;

    public HarGroup() {
    }

    public HarGroup(Short groupId) {
        this.groupId = groupId;
    }

    public HarGroup(Short groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    @XmlTransient
    public Collection<HarRepoGroup> getHarRepoGroupCollection() {
        return harRepoGroupCollection;
    }

    public void setHarRepoGroupCollection(Collection<HarRepoGroup> harRepoGroupCollection) {
        this.harRepoGroupCollection = harRepoGroupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarGroup)) {
            return false;
        }
        HarGroup other = (HarGroup) object;
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarGroup[ groupId=" + groupId + " ]";
    }
    
}
