package fi.helsinki.ohtu.orgrekouservice.domain;

public class NodeWrapper {
    private String parentNodeId;
    private String type;

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
