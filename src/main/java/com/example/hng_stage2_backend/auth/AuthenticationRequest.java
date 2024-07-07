package com.example.hng_stage2_backend.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
