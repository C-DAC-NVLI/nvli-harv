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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarCategory.findAll", query = "SELECT h FROM HarCategory h"),
    @NamedQuery(name = "HarCategory.findByCategoryId", query = "SELECT h FROM HarCategory h WHERE h.categoryId = :categoryId"),
    @NamedQuery(name = "HarCategory.findByCategoryName", query = "SELECT h FROM HarCategory h WHERE h.categoryName = :categoryName")})
public class HarCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "category_name", nullable = false, length = 500)
    private String categoryName;
    @Lob
    @Size(max = 65535)
    @Column(name = "category_desc", length = 65535)
    private String categoryDesc;

    public HarCategory() {
    }

    public HarCategory(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public HarCategory(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarCategory)) {
            return false;
        }
        HarCategory other = (HarCategory) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarCategory[ categoryId=" + categoryId + " ]";
    }
    
}
