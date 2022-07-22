package fi.helsinki.ohtu.orgrekouservice.domain;

public class Pair {

    private String childNodeId;

    private String parentNodeId;

    public String getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(String childNodeId) {
        this.childNodeId = childNodeId;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
}
