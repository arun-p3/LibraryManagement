package com.archonite.librarymanagement.system.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class DetailedInformation {

    @Column(name = "date_added")
    private LocalDate dateAdded;
    private boolean available;
    @Builder.Default
    private boolean damage = false;
    @Column(name = "stock_count")
    private int stockCount;
    private double price;
}
