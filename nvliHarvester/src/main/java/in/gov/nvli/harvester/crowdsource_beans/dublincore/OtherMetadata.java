package in.gov.nvli.harvester.crowdsource_beans.dublincore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * Used to store tag as tagName:[values] pair
 *
 * @author Madhuri
 */
public class OtherMetadata {

    private String tagName;
    
    private List<String> tagValues;
    
    private String parentName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<String> getTagValues() {
        if (tagValues == null) {
            tagValues = new ArrayList<>();
        }
        return tagValues;
    }

    public void setTagValues(List<String> tagValues) {
        this.tagValues = tagValues;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.tagName) >>> 20;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OtherMetadata other = (OtherMetadata) obj;
        if (!Objects.equals(this.tagName, other.tagName)) {
            return false;
        }
        return true;
    }
}