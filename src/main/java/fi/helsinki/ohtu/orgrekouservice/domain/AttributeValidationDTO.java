package fi.helsinki.ohtu.orgrekouservice.domain;

public class AttributeValidationDTO {
    private int id;
    private String nodeId;
    private String errorMessage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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

        AttributeValidationDTO that = (AttributeValidationDTO) o;

        if (getId() != that.getId()) return false;
        if (!getNodeId().equals(that.getNodeId())) return false;
        return getErrorMessage().equals(that.getErrorMessage());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getNodeId().hashCode();
        result = 31 * result + getErrorMessage().hashCode();
        return result;
    }
}
