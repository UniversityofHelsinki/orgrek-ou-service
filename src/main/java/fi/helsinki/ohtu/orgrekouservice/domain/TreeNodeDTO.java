package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeDTO {

    private String id;
    private String name;

    private String parentId;

    private String nameFi;

    private String nameEn;

    private String nameSv;

    private int uniqueId;

    private String code;

    ArrayList<String> hierarchies = new ArrayList<String>();

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

    public String getNameFi() {
        return nameFi;
    }

    public void setNameFi(String nameFi) {
        this.nameFi = nameFi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameSv() {
        return nameSv;
    }

    public void setNameSv(String nameSv) {
        this.nameSv = nameSv;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void addChild(TreeNodeDTO child) {
        if (!children.contains(child) && child != null)
            children.add(child);
    }

    public ArrayList<String> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(ArrayList<String> hierarchies) {
        this.hierarchies = hierarchies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNodeDTO that = (TreeNodeDTO) o;

        return getUniqueId() == that.getUniqueId();
    }

    @Override
    public int hashCode() {
        return getUniqueId();
    }
}
