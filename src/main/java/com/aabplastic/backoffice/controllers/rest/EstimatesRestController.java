package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.dto.EstimateDto;
import com.aabplastic.backoffice.models.dto.OrderDto;
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
    public EstimateDto createEstimate(@RequestBody @Valid EstimateDto estimateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        long orderId = estimateDto.getOrderId();

        EstimateDto estimate = orderService.getEstimateByOrderId(orderId);

        if (estimate != null) {
            return estimate;
        }

        EstimateDto created = orderService.createEstimate(estimateDto);

        return created;
    }

    public EstimateDto updateEstimate(@PathVariable long id, @RequestBody @Valid EstimateDto estimateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResourceValidationException("There are validation errors", bindingResult.getAllErrors());
        }

        EstimateDto savedEstimate = orderService.getEstimateById(id);


        return savedEstimate;
    }

}
