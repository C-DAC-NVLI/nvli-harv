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
@Table(name = "har_content_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarContentType.findAll", query = "SELECT h FROM HarContentType h"),
    @NamedQuery(name = "HarContentType.findByContentTypeId", query = "SELECT h FROM HarContentType h WHERE h.contentTypeId = :contentTypeId"),
    @NamedQuery(name = "HarContentType.findByContentTypeName", query = "SELECT h FROM HarContentType h WHERE h.contentTypeName = :contentTypeName")})
public class HarContentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "content_type_id", nullable = false)
    private Integer contentTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "content_type_name", nullable = false, length = 500)
    private String contentTypeName;
    @Lob
    @Size(max = 65535)
    @Column(name = "content_type_desc", length = 65535)
    private String contentTypeDesc;

    public HarContentType() {
    }

    public HarContentType(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public HarContentType(Integer contentTypeId, String contentTypeName) {
        this.contentTypeId = contentTypeId;
        this.contentTypeName = contentTypeName;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public String getContentTypeDesc() {
        return contentTypeDesc;
    }

    public void setContentTypeDesc(String contentTypeDesc) {
        this.contentTypeDesc = contentTypeDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contentTypeId != null ? contentTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarContentType)) {
            return false;
        }
        HarContentType other = (HarContentType) object;
        if ((this.contentTypeId == null && other.contentTypeId != null) || (this.contentTypeId != null && !this.contentTypeId.equals(other.contentTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarContentType[ contentTypeId=" + contentTypeId + " ]";
    }
    
}
