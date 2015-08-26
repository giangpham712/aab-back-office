package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.OrderItem;
import com.aabplastic.backoffice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
