package com.archonite.librarymanagement.system.inventory.dto;

import com.archonite.librarymanagement.system.account.dto.AccountRequestDto;
import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.catalog.dto.BookRequestDto;
import com.archonite.librarymanagement.system.catalog.model.BookModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private UUID id;
    private LocalDateTime orderDate;

    private List<BookRequestDto> book;

    private AccountRequestDto account;

    private LocalDate returnDate;

    private boolean due;
}
