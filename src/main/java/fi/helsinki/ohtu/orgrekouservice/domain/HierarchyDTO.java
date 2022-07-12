package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;

public class HierarchyDTO {

    private String hierarchy;
    private Date startDate;
    private Date endDate;

    public HierarchyDTO(Relative relative) {
        this.hierarchy = relative.getHierarchy();
        this.startDate = relative.getEdgeStartDate();
        this.endDate = relative.getEdgeEndDate();
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

}
