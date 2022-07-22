package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeDTO {

    private String id;
    private String name;

    private String parentId;

    private List<TreeNodeDTO> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public List<TreeNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeDTO> children) {
        this.children = children;
    }

    public void addChild(TreeNodeDTO child) {
        if (!children.contains(child) && child != null)
            children.add(child);
    }
}
