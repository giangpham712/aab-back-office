package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Customer;
import com.aabplastic.backoffice.models.dto.CustomerDto;
import com.aabplastic.backoffice.repositories.CustomerRepository;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<CustomerDto> getAllCustomers() {

        //TODO Implement caching
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDto> result = mapperFactory.getMapperFacade().mapAsList(customers, CustomerDto.class);

        return result;
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Customer customer = mapperFactory.getMapperFacade().map(customerDto, Customer.class);
        Date now = new Date();

        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        customer.setActive(true);

        customer = customerRepository.save(customer);
        return mapperFactory.getMapperFacade().map(customer, CustomerDto.class);
    }
}
