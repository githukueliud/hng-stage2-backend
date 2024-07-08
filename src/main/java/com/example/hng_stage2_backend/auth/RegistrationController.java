package com.example.hng_stage2_backend.auth;


import com.example.hng_stage2_backend.auth.response.Errors;
import com.example.hng_stage2_backend.auth.response.FailureAuthResponse;
import com.example.hng_stage2_backend.auth.response.RegistrationFieldsError;
import com.example.hng_stage2_backend.auth.response.SuccessAuthResponse;
import com.example.hng_stage2_backend.config.JwtService;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


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

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String email = registerRequest.getEmail();


        try {

            if (!email.matches(emailRegex)) {
                Errors errors = new Errors("Invalid email", "Kindly use as valid email");
                return ResponseEntity.unprocessableEntity().body( new RegistrationFieldsError(errors));
            }
            if (registerRequest.getFirstName().isEmpty()) {
                Errors errors = new Errors("Invalid First name entry", "First name cannot be empty");
                return ResponseEntity.unprocessableEntity().body( new RegistrationFieldsError(errors));
            }

            if (registerRequest.getLastName().isEmpty()) {
                Errors errors = new Errors("Invalid Last name entry", "Last name cannot be empty");
                return ResponseEntity.unprocessableEntity().body( new RegistrationFieldsError(errors));
            }

            if (registerRequest.getPassword().isEmpty()) {
                Errors errors = new Errors("Invalid password entry", "Password cannot be empty");
                return ResponseEntity.unprocessableEntity().body( new RegistrationFieldsError(errors));
            }




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

            //set creator and member
            organization.setCreator(user);
            organization.getMembers().add(user);

            //user.setOrganization(organization);
            user.setOrganizations(Collections.singletonList(organization));

            User savedUser = userRepository.save(user);

            String jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                    savedUser.getEmail(),
                    savedUser.getPassword(),
                    Collections.emptyList()
            ));

            SuccessAuthResponse.Data.User userResponse = new SuccessAuthResponse.Data.User(
                    savedUser.getUserId().toString(),
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
                savedUser.getUserId().toString(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhone()
        );

        SuccessAuthResponse.Data data = new SuccessAuthResponse.Data(jwtToken, userResponse);
        return new SuccessAuthResponse("success", "Registration successful", data);
    }
}
