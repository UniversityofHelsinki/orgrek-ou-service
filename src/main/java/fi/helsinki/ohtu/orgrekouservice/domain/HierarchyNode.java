package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.ArrayList;
import java.util.List;

public class HierarchyNode {

    private String id;
    private Integer uniqueId;
    private String name;
    private List<String> hierarchies;
    private List<HierarchyNode> children;

    public HierarchyNode() {
        this.setChildren(new ArrayList<>());
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
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

    public List<String> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<String> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public List<HierarchyNode> getChildren() {
        return children;
    }

    public void setChildren(List<HierarchyNode> children) {
        this.children = children;
    }
}
