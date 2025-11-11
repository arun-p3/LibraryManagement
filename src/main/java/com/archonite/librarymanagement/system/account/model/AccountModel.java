package com.archonite.librarymanagement.system.account.model;

import com.archonite.librarymanagement.system.account.dto.RoleAccess;
import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account_model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fullName;
    @Column(unique = true)
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleAccess role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    private List<OrderModel> orders;
}
