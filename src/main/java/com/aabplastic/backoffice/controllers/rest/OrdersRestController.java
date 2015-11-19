package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> listOrders(Model model, @RequestParam(value = "q", required = false) String search, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) throws Exception {

        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 1000;
        }

        if (search == null) {
            search = "";
        }

        Page<Order> orders = orderService.listOrders(search, page, limit, "orderNumber", Sort.Direction.ASC);
        return orders.getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }
        Order created = orderService.create(order);

        return created;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Order updateOrder(@PathVariable long id, @RequestBody @Valid Order order, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        Order updated = orderService.update(id, order);

        return updated;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long id) {

        orderService.delete(id);
    }
}
