package com.archonite.librarymanagement.system.inventory.model;

import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.catalog.model.BookModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order-model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime orderDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookModel> book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AccountModel account;

    private LocalDate returnDate;

    @Builder.Default
    private boolean due = false;
}
