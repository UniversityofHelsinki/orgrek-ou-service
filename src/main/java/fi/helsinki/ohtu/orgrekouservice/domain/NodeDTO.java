package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.List;

public class NodeDTO {
    private Node node;
    private List<String> hierarchies;


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<String> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<String> hierarchies) {
        this.hierarchies = hierarchies;
    }

}
