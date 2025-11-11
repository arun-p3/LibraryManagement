package com.archonite.librarymanagement.system.catalog.dto;

import com.archonite.librarymanagement.system.catalog.model.DetailedInformation;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record BookRequestDto(
        UUID id,
        String title,
        String isbn,
        String description,
        String author,
        String language,
        @Min(1) int qty,

        DetailedInformation detailedInformation
) {
}
