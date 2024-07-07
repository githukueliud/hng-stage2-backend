package com.example.hng_stage2_backend.organization.service.impl;


import com.example.hng_stage2_backend.organization.controller.request.CreateOrganizationRequest;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.entity.OrganizationDto;
import com.example.hng_stage2_backend.organization.entity.OrganizationMapperClass;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.organization.service.OrganizationService;
import com.example.hng_stage2_backend.user.entity.User;
import com.example.hng_stage2_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Override
    public Organization getOrganizationById(UUID orgId, String userEmail) {

        Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new RuntimeException("Organization not found!"));

        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found!"));

        // Check if the user created the organization or is a member
        boolean isCreator = organization.getCreatedBy().equals(userEmail);
        boolean isMember = organization.getMembers().contains(user.getId().toString());

        // Check membership via query result
        if (!isCreator && !isMember) {
            List<Organization> organizations = organizationRepository.findByMembersEmail(userEmail);
            if (!organizations.contains(organization)) {
                return null;
            }
        }

        return organization;
    }

    @Override
    public List<Organization> getOrganizationsForUser(String userId) {
        return organizationRepository.findOrganizationsByUserId(userId);
    }



    @Override
    public List<Organization> getUserOrganizations(String userEmail, String userId) {
        List<Organization> createdOrganizations = organizationRepository.findByUserEmail(userEmail);
        List<Organization> memberOrganizations = organizationRepository.findByMemberId(userId);

        // Combine both lists
        createdOrganizations.addAll(memberOrganizations);
        return createdOrganizations;
    }

    @Override
    public Organization createOrganization(CreateOrganizationRequest request, String userEmail) {

        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        organization.setCreatedBy(userEmail);
        return organizationRepository.save(organization);
    }

    @Override
    public void addUserToOrganization(UUID orgId, UUID userId) {
        Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new RuntimeException("Organization not found!"));

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));


        organization.getMembers().add(user);
        organizationRepository.save(organization);
    }

    @Override
    public Organization getMemberOrganizationById(UUID orgId, UUID userId) {
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found!"));

        // Step 2: Check if the user with userId is a member of the organization
        boolean isMember = organization.getMembers().stream()
                .anyMatch(memberId -> memberId.equals(userId.toString()));

        // Step 3: Return the organization if the user is a member, otherwise handle the case
        if (isMember) {
            return organization;
        } else {
            // Handle the case where the user is not a member (return null or throw an exception)
            return null; // or throw new RuntimeException("User is not a member of the organization!");
        }
    }


//    @Override
//    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
//        Organization org = OrganizationMapperClass.mapOrganizationDtoToOrganization(organizationDto);
//        Organization savedOrganization = organizationRepository.save(org);
//        return OrganizationMapperClass.mapOrganizationToOrganizationDto(savedOrganization);
//    }
//
//    @Override
//    public OrganizationDto updateOrganization(UUID id, OrganizationDto organizationDto) {
//        Organization org = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization not found!"));
//        org.setName(organizationDto.getName());
//        org.setDescription(organizationDto.getDescription());
//        Organization savedOrganization = organizationRepository.save(org);
//        return OrganizationMapperClass.mapOrganizationToOrganizationDto(savedOrganization);
//    }
//
//    @Override
//    public OrganizationDto getOrganization(UUID id) {
//        Organization org = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization not found!"));
//        return OrganizationMapperClass.mapOrganizationToOrganizationDto(org);
//    }
//
//    @Override
//    public List<OrganizationDto> listOrganization() {
//        List<Organization> organizationList = organizationRepository.findAll();
//        return organizationList.stream().map(OrganizationMapperClass::mapOrganizationToOrganizationDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteOrganization(UUID id) {
//        organizationRepository.deleteById(id);
//    }
}
