package com.example.hng_stage2_backend.auth.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FailureAuthResponse {
    private String status;
    private String message;
    private int statusCode;
}
