package com.example.hng_stage2_backend.organization.controller.response;



public class OrganizationSuccessResponse {
    private String status;
    private String message;
    private OrganizationResponseData data;

    public OrganizationSuccessResponse(String status, String message, OrganizationResponseData data) {
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

    public OrganizationResponseData getData() {
        return data;
    }

    public void setData(OrganizationResponseData data) {
        this.data = data;
    }
}

