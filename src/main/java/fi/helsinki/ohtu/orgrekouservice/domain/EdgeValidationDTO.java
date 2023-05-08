package fi.helsinki.ohtu.orgrekouservice.domain;

public class EdgeValidationDTO {
    private int id;
    private String parentNodeId;
    private int childUniqueId;
    private String errorMessage;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getParentNodeId() {
        return parentNodeId;
    }
    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    public int getChildUniqueId() {
        return childUniqueId;
    }
    public void setChildUniqueId(int childNodeId) {
        this.childUniqueId = childUniqueId;
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
        if (!getParentNodeId().equals(that.getParentNodeId())) return false;
        if (getChildUniqueId() != that.getChildUniqueId()) return false;
        return getErrorMessage().equals(that.getErrorMessage());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getParentNodeId().hashCode();
        result = 31 * result + getChildUniqueId();
        result = 31 * result + getErrorMessage().hashCode();
        return result;
    }
}
