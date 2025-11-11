package com.archonite.librarymanagement.system.account.dto;

import com.archonite.librarymanagement.system.inventory.dto.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {

    private UUID id;
    private String fullName;
    private String userName;
    private RoleAccess role;

}
