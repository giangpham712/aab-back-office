package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.ProductionReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionReadingRepository extends JpaRepository<ProductionReading, Long> {

    Iterable<ProductionReading> findByProductId(long id);
}
