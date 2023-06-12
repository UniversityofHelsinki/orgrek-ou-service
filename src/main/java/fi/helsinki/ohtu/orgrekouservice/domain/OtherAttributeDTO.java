package fi.helsinki.ohtu.orgrekouservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class OtherAttributeDTO {

    public static void fromAttribute(OtherAttributeDTO destination, Attribute attribute) {
        destination.setId(attribute.getId());
        destination.setKey(attribute.getKey());
        destination.setStartDate(attribute.getStartDate());
        destination.setEndDate(attribute.getEndDate());
        destination.setDeleted(attribute.isDeleted());
        destination.setNodeId(attribute.getNodeId());
        destination.setNew(attribute.isNew());
        destination.setValue(attribute.getValue());
        destination.setType("text");
    }

    private Integer id;
    private String nodeId;
    private String key;
    private String value;
    private Date startDate;
    private Date endDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> optionValues;

    private boolean isNew;

    private boolean deleted;

    private String type;


    public OtherAttributeDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<String> optionValues) {
        this.optionValues = optionValues;
    }

    @JsonProperty("isNew")
    public boolean isNew() {
        return this.isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
