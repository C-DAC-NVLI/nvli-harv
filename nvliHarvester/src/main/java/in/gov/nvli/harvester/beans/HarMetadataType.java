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
    @NamedQuery(name = "HarMetadataType.findByMetadataPrefix", query = "SELECT h FROM HarMetadataType h WHERE h.metadataPrefix = :metadataPrefix"),
    @NamedQuery(name = "HarMetadataType.findByMetadataSchema", query = "SELECT h FROM HarMetadataType h WHERE h.metadataSchema = :metadataSchema"),
    @NamedQuery(name = "HarMetadataType.findByMetadataNamespace", query = "SELECT h FROM HarMetadataType h WHERE h.metadataNamespace = :metadataNamespace")})
public class HarMetadataType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "metadata_id", nullable = false)
    private Short metadataId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "metadata_prefix", nullable = false, length = 100)
    private String metadataPrefix;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "metadata_schema", nullable = false, length = 500)
    private String metadataSchema;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "metadata_namespace", nullable = false, length = 500)
    private String metadataNamespace;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metadataTypeId")
    private Collection<HarMetadataTypeRepository> harMetadataTypeRepositoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metadataTypeId")
    private Collection<HarRecord> harRecordCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metadataTypeId")
    private Collection<HarRepoMetadata> harRepoMetadataCollection;

    public HarMetadataType() {
    }

    public HarMetadataType(Short metadataId) {
        this.metadataId = metadataId;
    }

    public HarMetadataType(String metadataPrefix, String metadataSchema, String metadataNamespace) {
        this.metadataPrefix = metadataPrefix;
        this.metadataSchema = metadataSchema;
        this.metadataNamespace = metadataNamespace;
    }

    public HarMetadataType(Short metadataId, String metadataPrefix, String metadataSchema, String metadataNamespace) {
        this.metadataId = metadataId;
        this.metadataPrefix = metadataPrefix;
        this.metadataSchema = metadataSchema;
        this.metadataNamespace = metadataNamespace;
    }

    public Short getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(Short metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    public String getMetadataSchema() {
        return metadataSchema;
    }

    public void setMetadataSchema(String metadataSchema) {
        this.metadataSchema = metadataSchema;
    }

    public String getMetadataNamespace() {
        return metadataNamespace;
    }

    public void setMetadataNamespace(String metadataNamespace) {
        this.metadataNamespace = metadataNamespace;
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

    @XmlTransient
    public Collection<HarRepoMetadata> getHarRepoMetadataCollection() {
        return harRepoMetadataCollection;
    }

    public void setHarRepoMetadataCollection(Collection<HarRepoMetadata> harRepoMetadataCollection) {
        this.harRepoMetadataCollection = harRepoMetadataCollection;
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
