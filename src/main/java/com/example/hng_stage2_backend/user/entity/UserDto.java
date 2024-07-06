package com.example.hng_stage2_backend.user.entity;


import com.example.hng_stage2_backend.organization.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Organization organization;
}
