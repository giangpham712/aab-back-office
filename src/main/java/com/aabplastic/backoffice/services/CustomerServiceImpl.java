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
    public Iterable<Customer> getAllCustomers() {

        //TODO Implement caching
        List<Customer> customers = customerRepository.findAll();

        return customers;
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

    public CustomerDto update(long id, CustomerDto customerDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        Date now = new Date();

        Customer savedCustomer = customerRepository.findOne(id);

        savedCustomer.setUpdatedAt(now);

        savedCustomer.setAlternatePhone(customerDto.getAlternatePhone());
        savedCustomer.setCompanyName(customerDto.getCompanyName());
        savedCustomer.setDisplayName(customerDto.getDisplayName());
        savedCustomer.setEmailAddress(customerDto.getEmailAddress());
        savedCustomer.setFirstName(customerDto.getFirstName());
        savedCustomer.setLastName(customerDto.getLastName());
        savedCustomer.setMiddleName(customerDto.getMiddleName());
        savedCustomer.setPrimaryPhone(customerDto.getPrimaryPhone());
        savedCustomer.setTitle(customerDto.getTitle());
        savedCustomer.setWebsite(customerDto.getWebsite());

        savedCustomer = customerRepository.save(savedCustomer);

        return mapperFactory.getMapperFacade().map(savedCustomer, CustomerDto.class);
    }
}
