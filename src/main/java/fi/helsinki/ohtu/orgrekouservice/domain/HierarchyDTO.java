package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;

public class HierarchyDTO {

    private String hierarchy;
    private Date startDate;
    private Date endDate;
    private Integer edgeId;

    public HierarchyDTO(Relative relative) {
        this.hierarchy = relative.getHierarchy();
        this.startDate = relative.getEdgeStartDate();
        this.endDate = relative.getEdgeEndDate();
        this.setEdgeId(relative.getEdgeId());
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
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
}
