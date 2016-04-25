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
@Table(name = "har_set")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarSet.findAll", query = "SELECT h FROM HarSet h"),
    @NamedQuery(name = "HarSet.findBySetId", query = "SELECT h FROM HarSet h WHERE h.setId = :setId"),
    @NamedQuery(name = "HarSet.findBySetName", query = "SELECT h FROM HarSet h WHERE h.setName = :setName"),
    @NamedQuery(name = "HarSet.findBySetSpec", query = "SELECT h FROM HarSet h WHERE h.setSpec = :setSpec")})
public class HarSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "set_id", nullable = false)
    private Long setId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "set_name", nullable = false, length = 500)
    private String setName;
    @Lob
    @Size(max = 65535)
    @Column(name = "set_desc", length = 65535)
    private String setDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "set_spec", nullable = false, length = 100)
    private String setSpec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "setId")
    private Collection<HarSetRepository> harSetRepositoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "setId")
    private Collection<HarSetRecord> harSetRecordCollection;

    public HarSet() {
    }

    public HarSet(Long setId) {
        this.setId = setId;
    }

    public HarSet(String setName, String setSpec) {
        this.setId = setId;
        this.setName = setName;
        this.setSpec = setSpec;
    }
    
    public HarSet(Long setId, String setName, String setSpec) {
        this.setId = setId;
        this.setName = setName;
        this.setSpec = setSpec;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public String getSetSpec() {
        return setSpec;
    }

    public void setSetSpec(String setSpec) {
        this.setSpec = setSpec;
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
        hash += (setId != null ? setId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarSet)) {
            return false;
        }
        HarSet other = (HarSet) object;
        if ((this.setId == null && other.setId != null) || (this.setId != null && !this.setId.equals(other.setId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarSet[ setId=" + setId + " ]";
    }
    
}
