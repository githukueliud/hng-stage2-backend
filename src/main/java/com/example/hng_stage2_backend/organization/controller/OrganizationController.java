package com.example.hng_stage2_backend.organization.controller;


import com.example.hng_stage2_backend.auth.response.FailureAuthResponse;
import com.example.hng_stage2_backend.organization.controller.request.CreateOrganizationRequest;
import com.example.hng_stage2_backend.organization.controller.response.OrganizationData;
import com.example.hng_stage2_backend.organization.controller.response.OrganizationResponseData;
import com.example.hng_stage2_backend.organization.controller.response.OrganizationSuccessResponse;
import com.example.hng_stage2_backend.organization.controller.response.create.CreateOrgData;
import com.example.hng_stage2_backend.organization.controller.response.create.CreateOrgSuccessResponse;
import com.example.hng_stage2_backend.organization.controller.response.id.OrgData;
import com.example.hng_stage2_backend.organization.controller.response.id.OrgSuccessResponse;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.organization.service.OrganizationService;
import com.example.hng_stage2_backend.user.entity.MyUserDetails;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganizationController {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationService organizationService;



    @GetMapping("/organisations")
    public ResponseEntity<?> getUserOrganizations(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("User is not authenticated!");
            }


            // get the user details based on authentication details
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User authenticatedUser = userRepository.findUserByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Authenticated user not found"));


            // get the organizations the user has created or is in
            List<Organization> organizations = organizationRepository.findByUserEmail(authenticatedUser.getEmail());


            // Map organizations to OrganizationData
            List<OrganizationData> organizationDataList = organizations.stream()
                    .map(org -> new OrganizationData(org.getOrgId().toString(), org.getName(), org.getDescription()))
                    .collect(Collectors.toList());

            OrganizationResponseData responseData = new OrganizationResponseData(organizationDataList);
            OrganizationSuccessResponse successResponse = new OrganizationSuccessResponse("success", "Organizations retrieved successfully", responseData);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Unauthorized", "Failed to retrieve organizations", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failureAuthResponse);
        }
    }



    @GetMapping("/organisations/{orgId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable UUID orgId, Authentication authentication) {

        String email = authentication.getName();


        // get organization and check if user has permission
        Organization organization = organizationService.getOrganizationById(orgId, email);


        if (organization != null) {
            OrgData orgData = new OrgData(organization.getOrgId().toString(), organization.getName(), organization.getDescription());
            OrgSuccessResponse orgSuccessResponse = new OrgSuccessResponse("success", organization.getDescription(), orgData);
            return ResponseEntity.ok(orgSuccessResponse);
        } else {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Unauthorized", "Failed to retrieve organizations", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failureAuthResponse);        }

    }



    @PostMapping("/organisations")
    public ResponseEntity<?> createOrganization(@Valid @RequestBody CreateOrganizationRequest request, Authentication authentication) {
        try {
            String userEmail = authentication.getName();

            Organization organization = organizationService.createOrganization(request, userEmail);

            CreateOrgData data = new CreateOrgData(organization.getOrgId().toString(), organization.getName(), organization.getDescription());
            CreateOrgSuccessResponse successResponse = new CreateOrgSuccessResponse("success", "Organisation created successfully", data);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(successResponse);
        } catch (Exception e) {
            FailureAuthResponse failureAuthResponse = new FailureAuthResponse("Bad Request", "Client error", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureAuthResponse);
        }
    }






}
