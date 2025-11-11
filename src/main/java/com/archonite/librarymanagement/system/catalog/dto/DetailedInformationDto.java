package com.archonite.librarymanagement.system.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedInformationDto {
    private LocalDate dateAdded;
    private boolean available;
    private boolean damage;
    private int stockCount;
    private double price;
}
