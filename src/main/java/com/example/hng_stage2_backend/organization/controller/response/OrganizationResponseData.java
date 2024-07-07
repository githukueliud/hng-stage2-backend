package com.example.hng_stage2_backend.organization.controller.response;



import java.util.List;

public class OrganizationResponseData {
    private List<OrganizationData> organisations;

    public OrganizationResponseData(List<OrganizationData> organisations) {
        this.organisations = organisations;
    }

    // Getter and setter
    public List<OrganizationData> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<OrganizationData> organisations) {
        this.organisations = organisations;
    }
}
