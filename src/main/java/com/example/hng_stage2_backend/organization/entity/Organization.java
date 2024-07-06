package com.example.hng_stage2_backend.organization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
}
