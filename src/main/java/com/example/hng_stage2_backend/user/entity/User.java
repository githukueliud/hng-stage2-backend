package com.example.hng_stage2_backend.user.entity;


import com.example.hng_stage2_backend.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "_user")
public class User {

    @Id
    @jakarta.validation.constraints.NotNull
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @jakarta.validation.constraints.NotNull
    @Column(nullable = false)
    private String firstName;

    @jakarta.validation.constraints.NotNull
    @Column(nullable = false)
    private String lastName;

    @jakarta.validation.constraints.NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @jakarta.validation.constraints.NotNull
    @Column(nullable = false)
    private String password;

    @jakarta.validation.constraints.NotNull
    @Column(nullable = false)
    private String phone;


    @ManyToMany
    @JoinTable(
            name = "user_organizations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private List<Organization> organizations = new ArrayList<>();
}
