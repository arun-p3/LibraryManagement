package com.archonite.librarymanagement.system.catalog.config;

import com.archonite.librarymanagement.system.catalog.model.BookModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class BookSearchCriteriaBuilder {

    public static Specification<BookModel> withFilters(String keyWord) {
        return (Root<BookModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (keyWord == null || keyWord.isBlank()) {
                return cb.conjunction(); // no filter if keyword is empty
            }

            String pattern = "%" + keyWord.toLowerCase() + "%";

            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), pattern);
            Predicate authorPredicate = cb.like(cb.lower(root.get("author")), pattern);
            Predicate isbnPredicate = cb.like(cb.lower(root.get("isbn")), pattern);
            Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), pattern);
            Predicate languagePredicate = cb.like(cb.lower(root.get("language")), pattern);

            return cb.or(titlePredicate, authorPredicate,
                    isbnPredicate, descriptionPredicate, languagePredicate);
        };
    }
}