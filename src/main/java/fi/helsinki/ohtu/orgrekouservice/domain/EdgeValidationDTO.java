package fi.helsinki.ohtu.orgrekouservice.domain;

public class EdgeValidationDTO {
    private int id;
    private int parentUniqueId;
    private int childUniqueId;
    private String errorMessage;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getParentUniqueId() {
        return parentUniqueId;
    }

    public void setParentUniqueId(int parentUniqueId) {
        this.parentUniqueId = parentUniqueId;
    }
    public int getChildUniqueId() {
        return childUniqueId;
    }
    public void setChildUniqueId(int childNodeId) {
        this.childUniqueId = childNodeId;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeValidationDTO that = (EdgeValidationDTO) o;

        if (getId() != that.getId()) return false;
        if (getParentUniqueId() != that.getParentUniqueId()) return false;
        if (getChildUniqueId() != that.getChildUniqueId()) return false;
        return getErrorMessage().equals(that.getErrorMessage());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getParentUniqueId();
        result = 31 * result + getChildUniqueId();
        result = 31 * result + getErrorMessage().hashCode();
        return result;
    }
}
