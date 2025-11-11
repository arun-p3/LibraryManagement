package com.archonite.librarymanagement.system.account.dto;


public record LoginRequest(
        String userName,
        String password,
        RoleAccess role
) {
}
