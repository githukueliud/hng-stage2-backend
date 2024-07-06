package com.example.hng_stage2_backend.auth;


import lombok.*;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
