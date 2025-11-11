package com.archonite.librarymanagement.system.catalog.service;

import com.archonite.librarymanagement.system.catalog.config.BookSearchCriteriaBuilder;
import com.archonite.librarymanagement.system.catalog.dto.BookResponseDto;
import com.archonite.librarymanagement.system.catalog.dto.BookSearchCriteria;
import com.archonite.librarymanagement.system.catalog.model.BookModel;
import com.archonite.librarymanagement.system.catalog.repository.CatalogRepository;
import com.archonite.librarymanagement.system.commonutils.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private CatalogRepository catalogRepository;

    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public List<BookResponseDto> search(String content) {
        Specification<BookModel> spec = BookSearchCriteriaBuilder.withFilters(content);
        List<BookModel> list = catalogRepository.findAll(spec);

        return list.stream()
                .map(l -> MapperUtils.map(l, BookResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookResponseDto> findAll(int page, int pageSize) {
        Page<BookModel> list = catalogRepository.findAll(PageRequest.of(page, pageSize));
        return list.map(l -> MapperUtils.map(l, BookResponseDto.class));
    }

    @Override
    public List<BookResponseDto> sort(String field) {
        List<BookModel> list = catalogRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return list.stream()
                .map(l -> MapperUtils.map(l, BookResponseDto.class))
                .collect(Collectors.toList());
    }
}
