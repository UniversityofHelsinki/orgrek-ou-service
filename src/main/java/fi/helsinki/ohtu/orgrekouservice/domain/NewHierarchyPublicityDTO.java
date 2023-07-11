package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.List;

public class NewHierarchyPublicityDTO {

    private String childId;
    private String hierarchy;
    private boolean publicity;

    private List<NameLanguageWrapper> names;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
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

    public List<NameLanguageWrapper> getNames() {
        return names;
    }

    public void setNames(List<NameLanguageWrapper> names) {
        this.names = names;
    }
}
