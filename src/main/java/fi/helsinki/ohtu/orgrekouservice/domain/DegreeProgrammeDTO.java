package fi.helsinki.ohtu.orgrekouservice.domain;

public class DegreeProgrammeDTO {

    private String nodeId;
    private String iamGroup;
    private String programmeCode;
    private String programmeNameFi;
    private String programmeNameEn;
    private String programmeNameSv;
    private String type;
    private String steeringGroupNameFi;
    private String steeringGroupNameSv;
    private String steeringGroupNameEn;

    public String getSteeringGroupNameFi() {
        return steeringGroupNameFi;
    }

    public void setSteeringGroupNameFi(String steeringGroupNameFi) {
        this.steeringGroupNameFi = steeringGroupNameFi;
    }

    public String getSteeringGroupNameSv() {
        return steeringGroupNameSv;
    }

    public void setSteeringGroupNameSv(String steeringGroupNameSv) {
        this.steeringGroupNameSv = steeringGroupNameSv;
    }

    public String getSteeringGroupNameEn() {
        return steeringGroupNameEn;
    }

    public void setSteeringGroupNameEn(String steeringGroupNameEn) {
        this.steeringGroupNameEn = steeringGroupNameEn;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getIamGroup() {
        return iamGroup;
    }

    public void setIamGroup(String iamGroup) {
        this.iamGroup = iamGroup;
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public String getProgrammeNameFi() {
        return programmeNameFi;
    }

    public void setProgrammeNameFi(String programmeNameFi) {
        this.programmeNameFi = programmeNameFi;
    }

    public String getProgrammeNameEn() {
        return programmeNameEn;
    }

    public void setProgrammeNameEn(String programmeNameEn) {
        this.programmeNameEn = programmeNameEn;
    }

    public String getProgrammeNameSv() {
        return programmeNameSv;
    }

    public void setProgrammeNameSv(String programmeNameSv) {
        this.programmeNameSv = programmeNameSv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
