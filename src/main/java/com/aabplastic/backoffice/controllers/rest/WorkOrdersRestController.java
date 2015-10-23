package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.dto.WorkOrderDto;
import com.aabplastic.backoffice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrdersRestController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderDto createWorkOrder(@RequestBody @Valid WorkOrderDto workOrderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        long orderId = workOrderDto.getOrderId();

        WorkOrderDto estimate = orderService.getWorkOrderByOrderId(orderId);

        if (estimate != null) {
            return estimate;
        }

        WorkOrderDto created = orderService.createWorkOrder(workOrderDto);

        return created;
    }

}
