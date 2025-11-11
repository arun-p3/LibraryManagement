package com.archonite.librarymanagement.system.catalog.repository;

import com.archonite.librarymanagement.system.catalog.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CatalogRepository extends JpaRepository<BookModel, UUID>, JpaSpecificationExecutor<BookModel> {
}
