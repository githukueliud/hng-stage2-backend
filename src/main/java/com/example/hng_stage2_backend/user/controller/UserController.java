package com.example.hng_stage2_backend.user.controller;


import com.example.hng_stage2_backend.user.entity.UserDto;
import com.example.hng_stage2_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;


    @PostMapping("/api/user-create")
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
}
