package com.example.hng_stage2_backend.organization.controller.response.id;

import com.example.hng_stage2_backend.organization.controller.response.OrganizationResponseData;

public class OrgSuccessResponse {
    private String status;
    private String message;
    private OrgData data;

    public OrgSuccessResponse(String status, String message, OrgData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrgData getData() {
        return data;
    }

    public void setData(OrgData data) {
        this.data = data;
    }
}
