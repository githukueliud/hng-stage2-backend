package com.example.hng_stage2_backend.auth;


import com.example.hng_stage2_backend.auth.response.FailureAuthResponse;
import com.example.hng_stage2_backend.auth.response.SuccessAuthResponse;
import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;
    private final JwtService jwtService;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            String organizationName = registerRequest.getFirstName() + "'s organization";
            Organization organization = new Organization();
            organization.setName(organizationName);
            organization.setUserEmail(registerRequest.getEmail());
            organization.setDescription(organizationName + " welcome");
            organization.setCreatedBy(registerRequest.getEmail());
            organization = organizationRepository.save(organization);

            User user = new User();
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setPhone(registerRequest.getPhone());
            //user.setOrganization(organization);
            user.setOrganizations(Collections.singletonList(organization));

            User savedUser = userRepository.save(user);

            String jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                    savedUser.getEmail(),
                    savedUser.getPassword(),
                    Collections.emptyList()
            ));

            SuccessAuthResponse.Data.User userResponse = new SuccessAuthResponse.Data.User(
                    savedUser.getId().toString(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getEmail(),
                    savedUser.getPhone()
            );

            SuccessAuthResponse.Data data = new SuccessAuthResponse.Data(jwtToken, userResponse);
            SuccessAuthResponse successAuthResponse = new SuccessAuthResponse("success", "Registration successful", data);

            return ResponseEntity.status(HttpStatus.CREATED).body(successAuthResponse);

        }  catch (Exception e) {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Bad Request", "Registration unsuccessful", 400);
            return ResponseEntity.badRequest().body(failureAuthResponse);
        }

    }

    private static SuccessAuthResponse getSuccessAuthResponse(User savedUser, String jwtToken) {
        SuccessAuthResponse.Data.User userResponse = new SuccessAuthResponse.Data.User(
                savedUser.getId().toString(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhone()
        );

        SuccessAuthResponse.Data data = new SuccessAuthResponse.Data(jwtToken, userResponse);
        return new SuccessAuthResponse("success", "Registration successful", data);
    }
}
