package com.example.hng_stage2_backend.organization.controller.response;

public class SuccessAddUserToOrganizationResponse {
    private String status;
    private String message;

    public SuccessAddUserToOrganizationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

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
}
