package com.archonite.librarymanagement.system.catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "book_model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String isbn;
    private String description;
    private String author;
    private String language;

    @Embedded
    DetailedInformation detailedInformation;
}
