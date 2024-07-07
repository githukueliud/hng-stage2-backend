package com.example.hng_stage2_backend.organization.repository;


import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    List<Organization> findByUserEmail(String email);
}
