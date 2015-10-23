package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.models.dto.CustomerDto;
import com.aabplastic.backoffice.models.dto.UserDto;
import com.aabplastic.backoffice.services.CustomerService;
import com.aabplastic.backoffice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomersRestController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@RequestBody @Valid CustomerDto customerDto) {
        CustomerDto created = customerService.create(customerDto);
        return created;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto create(@PathVariable long id, @RequestBody @Valid CustomerDto customerDto) {
        CustomerDto updated = customerService.update(id, customerDto);
        return updated;
    }
}
