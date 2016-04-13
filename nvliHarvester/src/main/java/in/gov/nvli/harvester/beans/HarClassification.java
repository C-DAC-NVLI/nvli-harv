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
@Table(name = "har_classification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarClassification.findAll", query = "SELECT h FROM HarClassification h"),
    @NamedQuery(name = "HarClassification.findByClassificationId", query = "SELECT h FROM HarClassification h WHERE h.classificationId = :classificationId"),
    @NamedQuery(name = "HarClassification.findByClassificationName", query = "SELECT h FROM HarClassification h WHERE h.classificationName = :classificationName")})
public class HarClassification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "classification_id")
    private Short classificationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "classification_name")
    private String classificationName;
    @Lob
    @Size(max = 65535)
    @Column(name = "classification_desc")
    private String classificationDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repoClassificationId")
    private Collection<HarRepoDetail> harRepoDetailCollection;

    public HarClassification() {
    }

    public HarClassification(Short classificationId) {
        this.classificationId = classificationId;
    }

    public HarClassification(Short classificationId, String classificationName) {
        this.classificationId = classificationId;
        this.classificationName = classificationName;
    }

    public Short getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Short classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getClassificationDesc() {
        return classificationDesc;
    }

    public void setClassificationDesc(String classificationDesc) {
        this.classificationDesc = classificationDesc;
    }

    @XmlTransient
    public Collection<HarRepoDetail> getHarRepoDetailCollection() {
        return harRepoDetailCollection;
    }

    public void setHarRepoDetailCollection(Collection<HarRepoDetail> harRepoDetailCollection) {
        this.harRepoDetailCollection = harRepoDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classificationId != null ? classificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarClassification)) {
            return false;
        }
        HarClassification other = (HarClassification) object;
        if ((this.classificationId == null && other.classificationId != null) || (this.classificationId != null && !this.classificationId.equals(other.classificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarClassification[ classificationId=" + classificationId + " ]";
    }
    
}
