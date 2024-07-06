package com.example.hng_stage2_backend.organization.service.impl;


import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.organization.entity.OrganizationDto;
import com.example.hng_stage2_backend.organization.entity.OrganizationMapperClass;
import com.example.hng_stage2_backend.organization.repository.OrganizationRepository;
import com.example.hng_stage2_backend.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;


    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        Organization org = OrganizationMapperClass.mapOrganizationDtoToOrganization(organizationDto);
        Organization savedOrganization = organizationRepository.save(org);
        return OrganizationMapperClass.mapOrganizationToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto updateOrganization(UUID id, OrganizationDto organizationDto) {
        Organization org = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization not found!"));
        org.setName(organizationDto.getName());
        org.setDescription(organizationDto.getDescription());
        Organization savedOrganization = organizationRepository.save(org);
        return OrganizationMapperClass.mapOrganizationToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganization(UUID id) {
        Organization org = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization not found!"));
        return OrganizationMapperClass.mapOrganizationToOrganizationDto(org);
    }

    @Override
    public List<OrganizationDto> listOrganization() {
        List<Organization> organizationList = organizationRepository.findAll();
        return organizationList.stream().map(OrganizationMapperClass::mapOrganizationToOrganizationDto).collect(Collectors.toList());
    }

    @Override
    public void deleteOrganization(UUID id) {
        organizationRepository.deleteById(id);
    }
}
