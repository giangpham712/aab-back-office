package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    List<Estimate> findByOrderId(long orderId);
}
