package com.example.hng_stage2_backend.auth;

import com.example.hng_stage2_backend.organization.entity.Organization;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    //private Organization organization;
}
