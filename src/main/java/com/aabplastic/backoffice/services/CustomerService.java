package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Customer;
import com.aabplastic.backoffice.models.dto.CustomerDto;
import com.aabplastic.backoffice.models.dto.UserDto;

import java.util.Collection;
import java.util.Iterator;

public interface CustomerService {

    Iterable<CustomerDto> getAllCustomers();

    CustomerDto create(CustomerDto customerEntry);
}
