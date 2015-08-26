package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<User> findByOrderNumber(String orderNumber);
}
