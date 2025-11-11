package com.archonite.librarymanagement.system.account.dto;

import com.archonite.librarymanagement.system.inventory.dto.OrderRequest;
import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public record AccountRequestDto(
        UUID id,
        String fullName,
        String userName,
        String password,

        RoleAccess role,

        List<OrderRequest> request
) {
}
