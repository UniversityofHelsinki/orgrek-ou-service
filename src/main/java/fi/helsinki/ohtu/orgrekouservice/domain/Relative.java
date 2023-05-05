package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;

public class Relative {
    private String id;
    private Integer uniqueId;
    private Date startDate;
    private Date endDate;
    private String hierarchy;

    private Integer edgeId;
    private Date edgeStartDate;
    private Date edgeEndDate;
    private String fullName;
    private String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(Integer edgeId) {
        this.edgeId = edgeId;
    }

    public Date getEdgeStartDate() {
        return edgeStartDate;
    }

    public void setEdgeStartDate(Date edgeStartDate) {
        this.edgeStartDate = edgeStartDate;
    }

    public Date getEdgeEndDate() {
        return edgeEndDate;
    }

    public void setEdgeEndDate(Date edgeEndDate) {
        this.edgeEndDate = edgeEndDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
