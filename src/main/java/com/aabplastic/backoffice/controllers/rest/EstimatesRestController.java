package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.services.ExpenseService;
import com.aabplastic.backoffice.services.MaterialService;
import com.aabplastic.backoffice.services.OrderService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/estimates")
public class EstimatesRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ExpenseService expenseService;

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

    @RequestMapping(value = "/{id}", params = "action=reset", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity performAction(@PathVariable long id) {

        Estimate savedEstimate = orderService.getEstimateById(id);

        if (savedEstimate == null) {
            throw new ResourceNotFoundException(MessageFormat.format("Estimate with id {0} cannot be found", id));
        }

        Order order = savedEstimate.getOrder();

        orderService.deleteEstimate(savedEstimate);

        Estimate newEstimate = createEstimateForOrder(order);
        orderService.createEstimate(newEstimate);

        return new ResponseEntity<Estimate>(newEstimate, HttpStatus.OK);
    }

    private Estimate createEstimateForOrder(Order order) {
        Estimate estimate = new Estimate();
        estimate.setOrderNumber(order.getOrderNumber());

        estimate.setOrderId(order.getId());

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        Iterable<Expense> defaultExpenses = expenseService.listDefaultExpenses();

        estimate.setItems(order.getItems().stream().map(orderItem -> {

            EstimateItem estimateItem = mapperFactory.getMapperFacade().map(orderItem, EstimateItem.class);
            estimateItem.setProductId(orderItem.getProductId());
            estimateItem.setOrderItemId(orderItem.getId());

            estimateItem.setMaterials(new ArrayList<>());
            estimateItem.setExpenses(new ArrayList<>());

            double gusset = (estimateItem.getBlowingWidth() - estimateItem.getWidth()) / 2;
            estimateItem.setGusset(gusset);

            double unitWeight = (estimateItem.getWidth() + gusset * 2) * estimateItem.getLength() * 0.09 * estimateItem.getThickness() * 2 / 100;
            estimateItem.setUnitWeight(unitWeight);

            double totalWeight = unitWeight * estimateItem.getQuantity() / 1000;
            estimateItem.setTotalWeight(totalWeight);

            double pricePerWeightUnit = estimateItem.getTotal() / totalWeight;
            estimateItem.setPricePerWeightUnit(pricePerWeightUnit);

            double actualThickness = calculateActualThickness(estimateItem.getThickness());
            estimateItem.setActualThickness(actualThickness);

            double actualUnitWeight = (estimateItem.getWidth() + gusset * 2) * estimateItem.getLength() * 0.09 * actualThickness * 2 / 110;
            estimateItem.setActualUnitWeight(actualUnitWeight);

            double actualTotalWeight = actualUnitWeight * estimateItem.getQuantity() / 1000;
            estimateItem.setActualTotalWeight(actualTotalWeight);

            double actualPricePerWeightUnit = estimateItem.getTotal() / actualTotalWeight;
            estimateItem.setActualPricePerWeightUnit(actualPricePerWeightUnit);

            List<EstimateItemExpense> expenses = StreamSupport.stream(defaultExpenses.spliterator(), false).map(x -> {
                EstimateItemExpense expense = new EstimateItemExpense();
                expense.setExpenseId(x.getId());
                expense.setTotal(x.getDefaultCost());
                return expense;
            }).collect(Collectors.toList());

            estimateItem.setExpenses(expenses);

            return estimateItem;

        }).collect(Collectors.toList()));

        return estimate;
    }

    private double calculateActualThickness(double thickness) {
        double tolerance;
        if (thickness < 0.013) {
            tolerance = 0;
        } else if (thickness < 0.014) {
            tolerance = 2;
        } else {
            tolerance = 5;
        }

        return (100 - tolerance) * thickness / 100;
    }
}
