package fi.helsinki.ohtu.orgrekouservice.domain;

public class FullNameDTO {
    private String language;
    private String name;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
