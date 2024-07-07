package com.example.hng_stage2_backend.organization.service;


import com.example.hng_stage2_backend.organization.controller.request.CreateOrganizationRequest;
import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.entity.OrganizationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrganizationService {
//    OrganizationDto createOrganization(OrganizationDto organizationDto);
//
//    OrganizationDto updateOrganization(UUID id, OrganizationDto organizationDto);
      Organization getOrganizationById(UUID orgId, String userEmail);

      Organization createOrganization(CreateOrganizationRequest request, String userEmail);
//    OrganizationDto getOrganization(UUID id);
//
//    List<OrganizationDto> listOrganization();
//
//    void deleteOrganization(UUID id);
}
