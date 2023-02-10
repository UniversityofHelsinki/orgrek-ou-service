package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.Date;

public class Attribute {

    private Integer id;
    private String nodeId;
    private String key;
    private String value;
    private Date startDate;
    private Date endDate;

    public Attribute() {
        this(null, null);
    }

    public Attribute(String key, String value) {
        this(null, key, value, null, null);
    }

    public Attribute(Integer id, String key, String value, Date startDate, Date endDate) {
        this(null, id, key, value, startDate, endDate);
    }

    public Attribute(String nodeId, Integer id, String key, String value, Date startDate, Date endDate) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nodeId = nodeId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        Attribute oa = (Attribute) o;
        if (this.id != null && oa.id != null && this.id == oa.id) {
            return true;
        } else if (this.id == null && oa.id == null) {
            return this.getKey().equals(oa.getKey()) &&
                    this.getValue().equals(oa.getValue());
        }
        return false;
    }
}
