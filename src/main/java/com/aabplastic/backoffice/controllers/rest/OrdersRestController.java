package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.exceptions.ResourceValidationException;
import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.services.ExpenseService;
import com.aabplastic.backoffice.services.OrderService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ExpenseService expenseService;

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

        updateEstimate(order);

        return updated;
    }

    private Estimate updateEstimate(Order order) {

        Estimate savedEstimate = orderService.getEstimateByOrderId(order.getId());

        if (savedEstimate == null) {
            return null;
        }

        Estimate updatedEstimate = createEstimateForOrder(order);

        List<EstimateItem> savedEstimateItems = savedEstimate.getItems();
        Map<Long, EstimateItem> map = savedEstimateItems.stream().collect(Collectors.toMap(EstimateItem::getProductId, Function.identity()));

        savedEstimate.getItems().stream()
                .sorted((i1, i2) -> (int) (i1.getId() - i2.getId()))
                .forEach(estimateItem -> {
                    savedEstimate.getItems().remove(estimateItem);

                });

        updatedEstimate.getItems().stream()
                .forEach(estimateItem -> {
                    savedEstimate.getItems().add(estimateItem);
                    estimateItem.setEstimate(savedEstimate);

                    long productId = estimateItem.getProductId();
                    if (map.containsKey(productId)) {
                        List<EstimateItemExpense> itemExpenses = map.get(productId).getExpenses();

                        itemExpenses.stream().forEach(itemExpense -> {
                            itemExpense.setEstimateItem(estimateItem);
                        });

                        estimateItem.setExpenses(itemExpenses);
                    } else {
                        List<EstimateItemExpense> itemExpenses = estimateItem.getExpenses();
                        itemExpenses.stream().forEach(itemExpense -> {
                            itemExpense.setEstimateItem(estimateItem);
                        });
                    }
                });

        orderService.updateEstimate(savedEstimate);

        return savedEstimate;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long id) {

        orderService.delete(id);
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
