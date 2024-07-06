package com.example.hng_stage2_backend.user.entity;

import java.util.UUID;

public class UserMapperClass {


    public static User mapUserDtoToUser(UserDto userDto) {
        return new User(
                UUID.randomUUID(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getPhone(),
                userDto.getOrganization()
        );
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getOrganization()
        );
    }



}
