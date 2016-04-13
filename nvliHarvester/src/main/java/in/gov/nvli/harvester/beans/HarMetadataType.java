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
@Table(name = "har_metadata_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarMetadataType.findAll", query = "SELECT h FROM HarMetadataType h"),
    @NamedQuery(name = "HarMetadataType.findByMetadataId", query = "SELECT h FROM HarMetadataType h WHERE h.metadataId = :metadataId"),
    @NamedQuery(name = "HarMetadataType.findByName", query = "SELECT h FROM HarMetadataType h WHERE h.name = :name")})
public class HarMetadataType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "metadata_id")
    private Short metadataId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metadataTypeId")
    private Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metadataTypeId")
    private Collection<HarRecord> harRecordCollection;

    public HarMetadataType() {
    }

    public HarMetadataType(Short metadataId) {
        this.metadataId = metadataId;
    }

    public HarMetadataType(Short metadataId, String name, String description) {
        this.metadataId = metadataId;
        this.name = name;
        this.description = description;
    }

    public Short getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(Short metadataId) {
        this.metadataId = metadataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<HarMetadataTypeRepository> getHarMetadataTypeRepositoryCollection() {
        return harMetadataTypeRepositoryCollection;
    }

    public void setHarMetadataTypeRepositoryCollection(Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection) {
        this.harMetadataTypeRepositoryCollection = harMetadataTypeRepositoryCollection;
    }

    @XmlTransient
    public Collection<HarRecord> getHarRecordCollection() {
        return harRecordCollection;
    }

    public void setHarRecordCollection(Collection<HarRecord> harRecordCollection) {
        this.harRecordCollection = harRecordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metadataId != null ? metadataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarMetadataType)) {
            return false;
        }
        HarMetadataType other = (HarMetadataType) object;
        if ((this.metadataId == null && other.metadataId != null) || (this.metadataId != null && !this.metadataId.equals(other.metadataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarMetadataType[ metadataId=" + metadataId + " ]";
    }
    
}
