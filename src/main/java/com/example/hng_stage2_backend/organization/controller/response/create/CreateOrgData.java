package com.example.hng_stage2_backend.organization.controller.response.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrgData {
    private String orgId;
    private String name;
    private String description;
}
