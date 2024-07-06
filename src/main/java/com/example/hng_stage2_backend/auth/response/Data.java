package com.example.hng_stage2_backend.auth.response;

import com.example.hng_stage2_backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String accessToken;
    private User user;
}
