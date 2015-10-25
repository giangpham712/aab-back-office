package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.Estimate;
import com.aabplastic.backoffice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/estimates")
public class EstimatesRestController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Estimate createEstimate(@RequestBody @Valid Estimate estimate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        long orderId = estimate.getOrderId();

        Estimate savedEstimate = orderService.getEstimateByOrderId(orderId);

        if (savedEstimate != null) {
            return estimate;
        }

        Estimate created = orderService.createEstimate(estimate);
        return created;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Estimate updateEstimate(@PathVariable long id, @RequestBody @Valid Estimate estimate, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        Estimate updated = orderService.updateEstimate(id, estimate);

        return updated;
    }

}
