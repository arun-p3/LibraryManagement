package com.archonite.librarymanagement.system.catalog.service;

import com.archonite.librarymanagement.system.catalog.dto.BookResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CatalogService {

    List<BookResponseDto> search(String content);

    Page<BookResponseDto> findAll(int page, int pageSize);

    List<BookResponseDto> sort(String field);

}
