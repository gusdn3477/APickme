package com.example.catalogservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
    Iterable<CatalogEntity> findByCreatedAtBetween(String start, String end);
    Iterable<CatalogEntity> findByProductNameContains(String name);
    Iterable<CatalogEntity> findByWriterContains(String name);
    @Transactional
    void deleteByproductId(String productId);

}

