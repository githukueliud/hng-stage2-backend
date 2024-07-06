package com.example.hng_stage2_backend.user.service;


import com.example.hng_stage2_backend.user.entity.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UUID id, UserDto userDto);

    void deleteUser(UUID id);

    List<UserDto> listAllUsers();

    UserDto getUser(UUID id);

}
