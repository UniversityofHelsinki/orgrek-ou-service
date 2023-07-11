package fi.helsinki.ohtu.orgrekouservice.domain;

public class HierarchyPublicity {
    private int id;
    private String hierarchy;
    private boolean publicity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public boolean isPublicity() {
        return publicity;
    }

    public void setPublicity(boolean publicity) {
        this.publicity = publicity;
    }
}
