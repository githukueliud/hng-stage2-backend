package com.example.hng_stage2_backend.organization.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUserToOrganizationRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

}
