package com.example.hng_stage2_backend.organization.controller.response;

public class OrganizationData {
    private String orgId;
    private String name;
    private String description;

    public OrganizationData(String orgId, String name, String description) {
        this.orgId = orgId;
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
