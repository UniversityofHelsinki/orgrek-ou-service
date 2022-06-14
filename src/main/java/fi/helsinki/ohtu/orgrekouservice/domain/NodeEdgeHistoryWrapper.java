package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;

public class NodeEdgeHistoryWrapper {
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date edgeStartDate;
    private Date edgeEndDate;
    private int uniqueId;

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
