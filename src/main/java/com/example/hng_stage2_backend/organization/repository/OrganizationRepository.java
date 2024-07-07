package com.example.hng_stage2_backend.organization.repository;


import com.example.hng_stage2_backend.organization.entity.Organization;
import com.example.hng_stage2_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    List<Organization> findByUserEmail(String email);

    @Query("SELECT o FROM Organization o JOIN o.members m WHERE m = :userId")
    List<Organization> findByMemberId(String userId);

    @Query("SELECT o FROM Organization o JOIN o.members m WHERE m.id = :userId")
    List<Organization> findOrganizationsByUserId(@Param("userId") String userId);

    @Query("SELECT DISTINCT o FROM Organization o " +
            "LEFT JOIN FETCH o.creator c " +
            "LEFT JOIN FETCH o.members m " +
            "WHERE c.email = :userEmail OR EXISTS (SELECT 1 FROM o.members m WHERE m.email = :userEmail)")
    List<Organization> findByMembersEmail(@Param("userEmail") String userEmail);
}
