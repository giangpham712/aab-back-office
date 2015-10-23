package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.models.Customer;
import com.aabplastic.backoffice.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String listCustomers(Model model) throws Exception {

        Iterable<Customer> customers = customerService.getAllCustomers();

        String jsonCustomers = new ObjectMapper().writeValueAsString(customers);
        model.addAttribute("customers", jsonCustomers);

        return "list-customers";
    }

    @RequestMapping(value = "/customers/new", method = RequestMethod.GET)
    public String newCustomer(Model model) {
        return "new-customer";
    }
}
