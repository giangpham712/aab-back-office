package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Customer;
import com.aabplastic.backoffice.models.dto.CustomerDto;

public interface CustomerService {

    Iterable<Customer> getAllCustomers();

    CustomerDto create(CustomerDto customerEntry);

    CustomerDto update(long id, CustomerDto customerDto);
}
