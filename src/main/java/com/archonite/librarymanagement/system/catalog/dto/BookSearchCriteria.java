package com.archonite.librarymanagement.system.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchCriteria {
    private UUID id;
    private String title;
    private String isbn;
    private String description;
    private String author;
}
