package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
