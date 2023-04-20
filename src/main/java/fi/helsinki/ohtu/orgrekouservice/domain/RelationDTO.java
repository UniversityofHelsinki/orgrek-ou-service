package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;
import java.util.List;

public class RelationDTO {
    private String id;
    private Integer uniqueId;
    private Date startDate;
    private Date endDate;
    private List<HierarchyDTO> hierarchies;
    private boolean isNew;
    private boolean deleted;

    private List<FullNameDTO> fullNames;

    public RelationDTO() {
    }

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

    public List<HierarchyDTO> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<HierarchyDTO> hierarchies) {
        this.hierarchies = hierarchies;
    }

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

    public List<FullNameDTO> getFullNames() {
        return fullNames;
    }

    public void setFullNames(List<FullNameDTO> fullNames) {
        this.fullNames = fullNames;
    }
}
