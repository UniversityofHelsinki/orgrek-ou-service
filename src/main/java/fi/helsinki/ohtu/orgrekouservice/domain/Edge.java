package fi.helsinki.ohtu.orgrekouservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Edge {
    private Integer id;
    private String childNodeId;
    private String parentNodeId;
    private int childUniqueId;
    private Date startDate;
    private Date endDate;
    private String hierarchy;
    private boolean isNew;
    private boolean deleted;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getChildNodeId() { return childNodeId; }
    public void setChildNodeId(String childNodeId) { this.childNodeId = childNodeId; }
    public String getParentNodeId() { return parentNodeId; }
    public void setParentNodeId(String parentNodeId) { this.parentNodeId = parentNodeId; }
    public int getChildUniqueId() { return childUniqueId; }
    public void setChildUniqueId(int childUniqueId) { this.childUniqueId = childUniqueId; }
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
    public String getHierarchy() {
        return hierarchy;
    }
    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }
    @JsonProperty("isNew")
    public boolean isNew() {
        return isNew;
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

}
