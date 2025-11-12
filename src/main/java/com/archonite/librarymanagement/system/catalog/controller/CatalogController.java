package com.archonite.librarymanagement.system.catalog.controller;

import com.archonite.librarymanagement.system.catalog.dto.BookResponseDto;
import com.archonite.librarymanagement.system.catalog.service.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/catalog-management")
public class CatalogController {


    private CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> searchBooks(@RequestParam("content") String content,
                                                             @RequestHeader("Authorization") String jwtToken) {

        List<BookResponseDto> response = catalogService.search(content);
        return ResponseEntity.ok(response);

    }


    @GetMapping("/book/findAll")
    public ResponseEntity<Page<BookResponseDto>> findAllBooks(@RequestHeader("Authorization") String jwtToken,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
        Page<BookResponseDto> response = catalogService.findAll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<BookResponseDto>> sortByFields(@RequestHeader("Authorization") String jwtToken,
                                                              @RequestParam("field") String field) {
        List<BookResponseDto> response = catalogService.sort(field);
        return ResponseEntity.ok(response);
    }
}
