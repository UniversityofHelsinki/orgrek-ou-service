package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.List;

public class TreeNode {

    private String childNodeId;
    private String parentNodeId;
    private Integer parentNodeUniqueId;
    private Integer childNodeUniqueId;

    private String parentCode;
    private String childCode;
    private String parentName;
    private String childName;
    private String language;
    private Boolean isRoot;
    private List<String> hierarchies;

    public Integer getParentNodeUniqueId() {
        return parentNodeUniqueId;
    }

    public void setParentNodeUniqueId(Integer parentNodeUniqueId) {
        this.parentNodeUniqueId = parentNodeUniqueId;
    }


    public Integer getChildNodeUniqueId() {
        return childNodeUniqueId;
    }

    public void setChildNodeUniqueId(Integer childNodeUniqueId) {
        this.childNodeUniqueId = childNodeUniqueId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parent_name) {
        this.parentName = parent_name;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<String> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

}
