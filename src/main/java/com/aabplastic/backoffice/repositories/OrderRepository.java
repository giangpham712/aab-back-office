package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByOrderNumberLikeAndDeletedFalse(String search, Pageable pageable);

    Page<Order> findByOrderNumberLikeOrOrderNameLikeAndDeletedFalse(String searchOrderNumber, String searchOrderName, Pageable pageable);

    List<Order> findAllByDeletedFalse();
}
