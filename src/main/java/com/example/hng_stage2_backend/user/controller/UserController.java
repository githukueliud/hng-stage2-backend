package com.example.hng_stage2_backend.user.controller;


import com.example.hng_stage2_backend.auth.response.FailureAuthResponse;
import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.user.controller.response.SuccessResponse;
import com.example.hng_stage2_backend.user.controller.response.SuccessResponseData;
import com.example.hng_stage2_backend.user.entity.MyUserDetails;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import com.example.hng_stage2_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable("id")UUID userId, Authentication authentication) {
        //ensure that the user is authenticated
        try {
            // Ensure user is authenticated
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("User is not authenticated");
            }

            // Retrieve user details based on authenticated user's identity
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User authenticatedUser = userRepository.findUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

            // Check if the requested user is the authenticated user or belongs to their organization
            User requestedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!requestedUser.getId().equals(authenticatedUser.getId())) {
                boolean isUserInSameOrganization = authenticatedUser.getOrganizations().stream()
                        .anyMatch(org -> requestedUser.getOrganizations().contains(org));
                if (!isUserInSameOrganization) {
                    throw new RuntimeException("Unauthorized access to user details");
                }
            }


            SuccessResponseData successResponseData = new SuccessResponseData(
                    requestedUser.getId().toString(),
                    requestedUser.getFirstName(),
                    requestedUser.getLastName(),
                    requestedUser.getEmail(),
                    requestedUser.getPhone()
            );

            SuccessResponse successResponse = new SuccessResponse("success", "User details retrieved successfully", successResponseData);
            return ResponseEntity.ok(successResponse);


        } catch (Exception e) {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Unauthorized", "Failed to retrieve user details", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failureAuthResponse);
        }
    }
}
