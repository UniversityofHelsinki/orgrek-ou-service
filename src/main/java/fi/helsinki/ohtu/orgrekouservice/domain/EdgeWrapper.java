package fi.helsinki.ohtu.orgrekouservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EdgeWrapper {

    public EdgeWrapper() { }

    public EdgeWrapper(Integer id, Integer parentUniqueId, Integer childUniqueId, String hierarchy, Date startDate, Date endDate, boolean isNew, boolean deleted) {
        setId(id);
        setParentUniqueId(parentUniqueId);
        setChildUniqueId(childUniqueId);
        setHierarchy(hierarchy);
        setStartDate(startDate);
        setEndDate(endDate);
        setNew(isNew);
        setDeleted(deleted);
    }
    private Integer id;
    private Integer parentUniqueId;
    private Integer childUniqueId;
    private Date startDate;
    private Date endDate;
    private String hierarchy;

    private boolean isNew;

    private boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentUniqueId() {
        return parentUniqueId;
    }

    public void setParentUniqueId(Integer parentUniqueId) {
        this.parentUniqueId = parentUniqueId;
    }

    public Integer getChildUniqueId() {
        return childUniqueId;
    }

    public void setChildUniqueId(Integer childUniqueId) {
        this.childUniqueId = childUniqueId;
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

