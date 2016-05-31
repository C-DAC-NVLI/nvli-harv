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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankit
 */
@Entity
@Table(name = "har_record_originalxml")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HarRecordOriginalxml.findAll", query = "SELECT h FROM HarRecordOriginalxml h"),
    @NamedQuery(name = "HarRecordOriginalxml.findByRecordorginalXMLid", query = "SELECT h FROM HarRecordOriginalxml h WHERE h.recordorginalXMLid = :recordorginalXMLid")})
public class HarRecordOriginalxml implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_orginalXML_id", nullable = false)
    private Long recordorginalXMLid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "record_xml_file", nullable = false)
    private byte[] recordXmlFile;
    @JoinColumn(name = "record_id", referencedColumnName = "record_id", nullable = false)
    @ManyToOne(optional = false)
    private HarRecord recordId;

    public HarRecordOriginalxml() {
    }

    public HarRecordOriginalxml(Long recordorginalXMLid) {
        this.recordorginalXMLid = recordorginalXMLid;
    }

    public HarRecordOriginalxml(Long recordorginalXMLid, byte[] recordXmlFile) {
        this.recordorginalXMLid = recordorginalXMLid;
        this.recordXmlFile = recordXmlFile;
    }

    public Long getRecordorginalXMLid() {
        return recordorginalXMLid;
    }

    public void setRecordorginalXMLid(Long recordorginalXMLid) {
        this.recordorginalXMLid = recordorginalXMLid;
    }

    public byte[] getRecordXmlFile() {
        return recordXmlFile;
    }

    public void setRecordXmlFile(byte[] recordXmlFile) {
        this.recordXmlFile = recordXmlFile;
    }

    public HarRecord getRecordId() {
        return recordId;
    }

    public void setRecordId(HarRecord recordId) {
        this.recordId = recordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordorginalXMLid != null ? recordorginalXMLid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HarRecordOriginalxml)) {
            return false;
        }
        HarRecordOriginalxml other = (HarRecordOriginalxml) object;
        if ((this.recordorginalXMLid == null && other.recordorginalXMLid != null) || (this.recordorginalXMLid != null && !this.recordorginalXMLid.equals(other.recordorginalXMLid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "in.gov.nvli.harvester.beans.HarRecordOriginalxml[ recordorginalXMLid=" + recordorginalXMLid + " ]";
    }
    
}
