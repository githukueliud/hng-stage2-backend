package com.example.hng_stage2_backend.organization.controller.request;

import jakarta.validation.constraints.NotBlank;

public class CreateOrganizationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    // Getters and setters
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
