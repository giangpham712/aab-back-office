package com.aabplastic.backoffice.repositories;

import com.aabplastic.backoffice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
