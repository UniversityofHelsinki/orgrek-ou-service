package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.List;

public class NodeDTO {
    private Node node;
    private NodeEdgeHistoryWrapper nodeEdgeHistoryWrapper;
    private List<NodeWrapper> hierarchies;
    private List<Attribute> attributes;
    private String displayNameFi;
    private String displayNameSv;
    private String displayNameEn;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<NodeWrapper> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<NodeWrapper> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public String getDisplayNameFi() {
        return displayNameFi;
    }

    public void setDisplayNameFi(String displayNameFi) {
        this.displayNameFi = displayNameFi;
    }

    public String getDisplayNameSv() {
        return displayNameSv;
    }

    public void setDisplayNameSv(String displayNameSv) {
        this.displayNameSv = displayNameSv;
    }

    public String getDisplayNameEn() {
        return displayNameEn;
    }

    public void setDisplayNameEn(String displayNameEn) {
        this.displayNameEn = displayNameEn;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    public NodeEdgeHistoryWrapper getNodeEdgeHistoryWrapper() {
        return nodeEdgeHistoryWrapper;
    }

    public void setNodeEdgeHistoryWrapper(NodeEdgeHistoryWrapper nodeEdgeHistoryWrapper) {
        this.nodeEdgeHistoryWrapper = nodeEdgeHistoryWrapper;
    }

}
