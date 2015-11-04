package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.models.dto.OrderDto;
import com.aabplastic.backoffice.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class EstimatesController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ExpenseService expenseService;

    @RequestMapping(value = "/estimates/new", method = RequestMethod.GET)
    public String newEstimate(
            Model model,
            @RequestParam(value = "orderId", required = true) long orderId,
            RedirectAttributes redirectAttributes
    )
            throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        OrderDto order = orderService.getOrderById(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Not found");
        }

        Estimate existingEstimate = orderService.getEstimateByOrderId(orderId);

        if (existingEstimate != null) {
            String redirectUrl = "estimates/edit/" + existingEstimate.getId();
            return "redirect:/" + redirectUrl;
        }

        Estimate estimate = new Estimate();
        estimate.setOrderNumber(order.getOrderNumber());

        estimate.setOrderId(order.getId());

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

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

            return estimateItem;

        }).collect(Collectors.toList()));

        String jsonOrder = objectMapper.writeValueAsString(order);
        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("mode", "new");
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", jsonEstimate);

        prepareViewData(model);

        return "edit-estimate";
    }

    @RequestMapping(value = "/estimates/edit/{id}", method = RequestMethod.GET)
    public String editEstimate(Model model, @PathVariable long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        Estimate estimate = orderService.getEstimateById(id);

        if (estimate == null) {
            throw new ResourceNotFoundException("Not found");
        }

        OrderDto order = orderService.getOrderById(estimate.getOrderId());

        String jsonOrder = objectMapper.writeValueAsString(order);

        estimate.setOrderNumber(order.getOrderNumber());

        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("mode", "edit");
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", jsonEstimate);

        prepareViewData(model);

        return "edit-estimate";
    }

    private void prepareViewData(Model model) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        Iterable<Product> products = productService.listProducts();
        String jsonProducts = objectMapper.writeValueAsString(products);

        Iterable<Material> materials = materialService.listMaterials();
        String jsonMaterials = objectMapper.writeValueAsString(materials);

        Iterable<Expense> expenses = expenseService.listExpenses();
        String jsonExpenses = objectMapper.writeValueAsString(expenses);

        model.addAttribute("products", jsonProducts);
        model.addAttribute("materials", jsonMaterials);
        model.addAttribute("expenses", jsonExpenses);
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
