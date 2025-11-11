package com.archonite.librarymanagement.system.catalog.dto;

import com.archonite.librarymanagement.system.catalog.model.DetailedInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDto {
    private UUID id;
    private String title;
    private String isbn;
    private String description;
    private String author;
    private String language;
    private DetailedInformationDto detailedInformation;
}
