package com.example.hng_stage2_backend.organization.entity;

import com.example.hng_stage2_backend.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue
    @jakarta.validation.constraints.NotNull
    @Column(nullable = false, unique = true)
    private UUID orgId;

    @NotBlank(message = "Organization name is required")
    @Column(nullable = false)
    private String name;


    private String description;

    private String userEmail;

    private String createdBy;


    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "organization_members",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @ManyToMany(mappedBy = "organizations")
    private List<User> users = new ArrayList<>();



}
