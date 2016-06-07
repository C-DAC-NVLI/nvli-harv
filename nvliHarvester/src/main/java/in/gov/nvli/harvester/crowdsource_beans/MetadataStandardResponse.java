package in.gov.nvli.harvester.crowdsource_beans;

import in.gov.nvli.harvester.crowdsource_beans.dublincore.DublinCoreMetadata;
import java.io.Serializable;


/**
 * Use for generating JSON format for DC Metadata
 *
 * @author Madhuri
 */
public class MetadataStandardResponse implements Serializable{

    private String recordIdentifier;
    
    private String metadataStandard;

  
    private DublinCoreMetadata metadata;
    
    private String isCloseForEdit;
  
    public String getMetadataStandard() {
        return metadataStandard;
    }

    public void setMetadataStandard(String metadataStandard) {
        this.metadataStandard = metadataStandard;
    }

    public String getIsCloseForEdit() {
        return isCloseForEdit;
    }

    public void setIsCloseForEdit(String isCloseForEdit) {
        this.isCloseForEdit = isCloseForEdit;
    }

    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
    }

    public DublinCoreMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DublinCoreMetadata metadata) {
        this.metadata = metadata;
    }

  
    
}