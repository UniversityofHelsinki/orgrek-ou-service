package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelativeDTO {
    private String id;


    private Integer edgeId;
    private Integer uniqueId;
    private Date startDate;
    private Date endDate;
    private List<HierarchyDTO> hierarchies;
    private String fullName;
    private String language;

    public RelativeDTO(Relative relative) {
        this.id = relative.getId();
        this.uniqueId = relative.getUniqueId();
        this.startDate = relative.getStartDate();
        this.endDate = relative.getEndDate();
        this.fullName = relative.getFullName();
        this.language = relative.getLanguage();
        this.edgeId = relative.getEdgeId();
        this.hierarchies = new ArrayList<>();
    }

    public void addHierarchy(Relative relative) {
        this.hierarchies.add(new HierarchyDTO(relative));
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

    public Integer getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(Integer edgeId) {
        this.edgeId = edgeId;
    }
    public List<HierarchyDTO> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<HierarchyDTO> hierarchies) {
        this.hierarchies = hierarchies;
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
