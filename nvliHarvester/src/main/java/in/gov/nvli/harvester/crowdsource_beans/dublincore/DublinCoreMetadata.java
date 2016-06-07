package in.gov.nvli.harvester.crowdsource_beans.dublincore;

import java.io.Serializable;
import java.util.List;
import javax.json.JsonObject;

/**
 *
 * @author Saurabh Koriya
 * @version 1
 * @since 1
 */
public class DublinCoreMetadata implements Serializable{
    /**
     * title of record.
     */
    private List<String> title;
    /**
     * description of record.
     */
    private List<String> description;
    /**
     * rights of record.
     */
    private List<String> rights;
    /**
     * date of record.
     */
    private List<String> date;
    /**
     * creator of record.
     */
    private List<String> creator;
    /**
     * publisher of record.
     */
    private List<String> publisher;
    /**
     * format of record.
     */
    private List<String> format;
    /**
     * relation of record.
     */
    private List<String> relation;
    /**
     * coverage of record.
     */
    private List<String> coverage;
    /**
     * language of record.
     */
    private List<String> language;
    /**
     * identifier details.
     */
    private List<String> identifier;
    /**
     * subject of record.
     */
    private List<String> subject;
    /**
     * source of record.
     */
    private List<String> source;
    /**
     * type of record.
     */
    private List<String> type;
    /**
     * contributor of record.
     */
    private List<String> contributor;
    /**
     * other tags information
     */
    private List<OtherMetadata> others;

    public List<OtherMetadata> getOthers() {
        return others;
    }

    public void setOthers(List<OtherMetadata> others) {
        this.others = others;
    }

    public List<String> getContributor() {
        return contributor;
    }

    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    public List<String> getCoverage() {
        return coverage;
    }

    public void setCoverage(List<String> coverage) {
        this.coverage = coverage;
    }

    public List<String> getCreator() {
        return creator;
    }

    public void setCreator(List<String> creator) {
        this.creator = creator;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getPublisher() {
        return publisher;
    }

    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }

    public List<String> getRelation() {
        return relation;
    }

    public void setRelation(List<String> relation) {
        this.relation = relation;
    }

    public List<String> getRights() {
        return rights;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public List<String> getSubject() {
        return subject;
    }

    public void setSubject(List<String> subject) {
        this.subject = subject;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return null;
    }


}