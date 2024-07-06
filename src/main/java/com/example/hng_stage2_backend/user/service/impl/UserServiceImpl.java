package com.example.hng_stage2_backend.user.service.impl;


import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.entity.UserDto;
import com.example.hng_stage2_backend.user.entity.UserMapperClass;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import com.example.hng_stage2_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapperClass.mapUserDtoToUser(userDto);
        User savedUser = repository.save(user);
        return UserMapperClass.mapUserToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());
        user.setPhone(userDto.getPhone());
        User savedUser = repository.save(user);
        return UserMapperClass.mapUserToUserDto(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDto> listAllUsers() {
        List<User> userList = repository.findAll();
        return userList.stream().map(UserMapperClass::mapUserToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        return UserMapperClass.mapUserToUserDto(user);
    }
}
