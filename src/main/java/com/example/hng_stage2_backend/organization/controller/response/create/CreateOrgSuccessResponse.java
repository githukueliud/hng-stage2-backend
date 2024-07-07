package com.example.hng_stage2_backend.organization.controller.response.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrgSuccessResponse {
    private String status;
    private String message;
    private CreateOrgData data;
}
