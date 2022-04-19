package fi.helsinki.ohtu.orgrekouservice.domain;

import java.util.List;

public class User {
    private String eppn;
    private String preferredLanguage;
    private String hyGroupCn;
    private String displayName;
    private List<String> roles;

    public String getEppn() {
        return eppn;
    }

    public void setEppn(String eppn) {
        this.eppn = eppn;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getHyGroupCn() {
        return hyGroupCn;
    }

    public void setHyGroupCn(String hyGroupCn) {
        this.hyGroupCn = hyGroupCn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
