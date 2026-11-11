package fi.helsinki.ohtu.orgrekouservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class EdgeWrapper {

    public EdgeWrapper() { }

    public EdgeWrapper(Integer id, Integer parentUniqueId, Integer childUniqueId, String hierarchy, LocalDate startDate, LocalDate endDate, Boolean isNew, Boolean deleted) {
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
    private LocalDate startDate;
    private LocalDate endDate;
    private String hierarchy;

    private Boolean isNew;

    private Boolean deleted;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    @JsonProperty("isNew")
    public Boolean isNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

