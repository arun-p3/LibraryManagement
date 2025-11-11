package com.archonite.librarymanagement.system.account.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SignUpRequestDto(
        @NotBlank(message = "Name cannot be Empty!") String fullName,
        @Positive(message = "Enter Valid Age")  int age,
        @NotBlank(message = "username shouldn't be empty")
        @Column(nullable = false, unique = true)
        String userName,
        @NotBlank String password,
        RoleAccess role
) {}
