package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByOrderNumberLike(String search, Pageable pageable);
}
