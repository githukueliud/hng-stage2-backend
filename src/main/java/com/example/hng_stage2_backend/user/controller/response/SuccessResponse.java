package com.example.hng_stage2_backend.user.controller.response;

public class SuccessResponse {
    private String status;
    private String message;
    private SuccessResponseData data;

    public SuccessResponse(String status, String message, SuccessResponseData data) {
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

    public SuccessResponseData getData() {
        return data;
    }

    public void setData(SuccessResponseData data) {
        this.data = data;
    }
}

