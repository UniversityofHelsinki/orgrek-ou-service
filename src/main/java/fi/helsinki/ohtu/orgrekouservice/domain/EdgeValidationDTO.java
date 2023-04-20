package fi.helsinki.ohtu.orgrekouservice.domain;

public class EdgeValidationDTO {

    private int id;

    private String parentNodeId;

    private String childNodeId;
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

    public String getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(String childNodeId) {
        this.childNodeId = childNodeId;
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
        if (!getChildNodeId().equals(that.getChildNodeId())) return false;
        return getErrorMessage().equals(that.getErrorMessage());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getParentNodeId().hashCode();
        result = 31 * result + getChildNodeId().hashCode();
        result = 31 * result + getErrorMessage().hashCode();
        return result;
    }
}
