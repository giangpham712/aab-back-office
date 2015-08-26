package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.exceptions.ResourceNotFoundException;
import com.aabplastic.backoffice.models.Estimate;
import com.aabplastic.backoffice.models.dto.*;
import com.aabplastic.backoffice.services.CustomerService;
import com.aabplastic.backoffice.services.ItemService;
import com.aabplastic.backoffice.services.OrderService;
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

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String listOrders(Model model) throws Exception {

        Iterable<OrderDto> orders = orderService.getAllOrders();

        Iterable<CustomerDto> customers = customerService.getAllCustomers();

        Map<Long, CustomerDto> customersMap = new HashMap<>();

        customers.forEach(customerDto -> {
            customersMap.put(customerDto.getId(), customerDto);
        });

        for (OrderDto order : orders) {
            order.setCustomer(customersMap.get(order.getCustomerId()));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        String jsonOrders = objectMapper.writeValueAsString(orders);
        model.addAttribute("orders", jsonOrders);

        return "list-orders";
    }

    @RequestMapping(value = "/orders/new", method = RequestMethod.GET)
    public String newOrder(Model model) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        Iterable<CustomerDto> customers = customerService.getAllCustomers();
        String jsonCustomers = objectMapper.writeValueAsString(customers);

        Iterable<ItemDto> items = itemService.listItemsByType(ItemType.ASSEMBLY_ITEM, new String[]{"attributes", "billsOfMaterials"});
        String jsonItems = objectMapper.writeValueAsString(items);

        model.addAttribute("headerTitle", "New order");
        model.addAttribute("customers", jsonCustomers);
        model.addAttribute("items", jsonItems);
        model.addAttribute("order", "{}");
        model.addAttribute("estimate", objectMapper.writeValueAsString(null));

        model.addAttribute("mode", "new");

        return "edit-order";
    }

    @RequestMapping(value = "/orders/edit/{id}", method = RequestMethod.GET)
    public String editOrder(Model model, @PathVariable long id) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        OrderDto order = orderService.getOrderById(id);

        if (order == null) {
            throw new ResourceNotFoundException("Not found");
        }

        String jsonOrder = objectMapper.writeValueAsString(order);

        Iterable<CustomerDto> customers = customerService.getAllCustomers();
        String jsonCustomers = objectMapper.writeValueAsString(customers);

        Iterable<ItemDto> items = itemService.listItemsByType(ItemType.ASSEMBLY_ITEM, new String[]{"attributes", "billsOfMaterials"});
        String jsonItems = objectMapper.writeValueAsString(items);

        EstimateDto estimate = orderService.getEstimateByOrderId(order.getId());
        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("order", jsonOrder);
        model.addAttribute("headerTitle", order.getOrderNumber());
        model.addAttribute("customers", jsonCustomers);
        model.addAttribute("items", jsonItems);
        model.addAttribute("estimate", jsonEstimate);

        model.addAttribute("mode", "edit");
        return "edit-order";
    }

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

        EstimateDto existingEstimate = orderService.getEstimateByOrderId(orderId);

        if (existingEstimate != null) {
            String redirectUrl = "estimates/edit/" + existingEstimate.getId();
            return "redirect:/" + redirectUrl;
        }

        EstimateDto estimate = new EstimateDto();
        estimate.setOrderNumber(order.getOrderNumber());
        estimate.setOrderId(order.getId());

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        estimate.setItems(order.getItems().stream().map(x -> {

            EstimateItemDto estimateItem = mapperFactory.getMapperFacade().map(x, EstimateItemDto.class);
            estimateItem.setOrderItemId(x.getId());
            double gusset = (estimateItem.getBlowingWidth() - estimateItem.getWidth()) / 2;
            estimateItem.setGusset(gusset);
            return estimateItem;

        }).collect(Collectors.toList()));


        Iterable<ItemDto> assemblyItems = itemService.listItemsByType(ItemType.ASSEMBLY_ITEM, new String[]{"attributes", "billsOfMaterials"});
        String jsonAssemblyItems = objectMapper.writeValueAsString(assemblyItems);

        Iterable<ItemDto> inventoryItems = itemService.listItemsByType(ItemType.INVENTORY_ITEM, new String[]{"billsOfMaterials"});
        String jsonInventoryItems = objectMapper.writeValueAsString(inventoryItems);

        Iterable<ItemDto> expenseItems = itemService.listItemsByType(ItemType.EXPENSE_ITEM, new String[]{"billsOfMaterials"});
        String jsonExpenseItems = objectMapper.writeValueAsString(expenseItems);

        String jsonOrder = objectMapper.writeValueAsString(order);
        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("mode", "new");
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", jsonEstimate);

        model.addAttribute("assemblyItems", jsonAssemblyItems);
        model.addAttribute("inventoryItems", jsonInventoryItems);
        model.addAttribute("expenseItems", jsonExpenseItems);

        return "edit-estimate";
    }

    @RequestMapping(value = "/estimates/edit/{id}", method = RequestMethod.GET)
    public String editEstimate(Model model, @PathVariable long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        EstimateDto estimate = orderService.getEstimateById(id);

        if (estimate == null) {
            throw new ResourceNotFoundException("Not found");
        }


        OrderDto order = orderService.getOrderById(estimate.getOrderId());
        String jsonOrder = objectMapper.writeValueAsString(order);

        estimate.setOrderNumber(order.getOrderNumber());

        Iterable<ItemDto> assemblyItems = itemService.listItemsByType(ItemType.ASSEMBLY_ITEM, new String[]{"attributes", "billsOfMaterials"});
        String jsonAssemblyItems = objectMapper.writeValueAsString(assemblyItems);

        Iterable<ItemDto> inventoryItems = itemService.listItemsByType(ItemType.INVENTORY_ITEM, new String[]{"billsOfMaterials"});
        String jsonInventoryItems = objectMapper.writeValueAsString(inventoryItems);

        Iterable<ItemDto> expenseItems = itemService.listItemsByType(ItemType.EXPENSE_ITEM, new String[]{"billsOfMaterials"});
        String jsonExpenseItems = objectMapper.writeValueAsString(expenseItems);

        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("mode", "edit");
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", jsonEstimate);

        model.addAttribute("assemblyItems", jsonAssemblyItems);
        model.addAttribute("inventoryItems", jsonInventoryItems);
        model.addAttribute("expenseItems", jsonExpenseItems);

        return "edit-estimate";
    }

    @RequestMapping(value = "/workorders/new", method = RequestMethod.GET)
    public String newWorkOrder(
            Model model,
            @RequestParam(value = "estimateId", required = true) long orderId,
            RedirectAttributes redirectAttributes
    )
            throws JsonProcessingException {
        return "edit-workorder";
    }
}
