package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameLikeAndDeletedFalse(String format, Pageable pageable);
}
