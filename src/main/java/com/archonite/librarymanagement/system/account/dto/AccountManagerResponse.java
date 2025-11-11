package com.archonite.librarymanagement.system.account.dto;


import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class AccountManagerResponse {

    private UUID id;
    private String fullName;
    private String userName;
    private RoleAccess role;

    private List<OrderModel> orders;
}
