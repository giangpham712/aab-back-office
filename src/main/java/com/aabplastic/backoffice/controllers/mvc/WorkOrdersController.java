package com.aabplastic.backoffice.controllers.mvc;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.dto.*;
import com.aabplastic.backoffice.services.ItemService;
import com.aabplastic.backoffice.services.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Controller
public class WorkOrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/workorders/new", method = RequestMethod.GET)
    public String newWorkOrder(
            Model model,
            @RequestParam(value = "orderId", required = true) long orderId,
            RedirectAttributes redirectAttributes
    )
            throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        OrderDto order = orderService.getOrderById(orderId);

        EstimateDto estimate = orderService.getEstimateByOrderId(orderId);

        WorkOrderDto workOrderDto = new WorkOrderDto();

        workOrderDto.setOrderNumber(order.getOrderNumber());
        workOrderDto.setOrderId(order.getId());

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        workOrderDto.setItems(estimate.getItems().stream().map(x -> {

            WorkOrderItemDto workOrderItem = new WorkOrderItemDto();

            // Dimensions
            workOrderItem.setThickness(x.getActualThickness());
            workOrderItem.setBlowingWidth(x.getBlowingWidth());
            workOrderItem.setWidth(x.getWidth());
            workOrderItem.setGusset(x.getGusset());
            workOrderItem.setLength(x.getLength());
            workOrderItem.setHandleRatio(x.getHandleRatio());

            workOrderItem.setQuantity(x.getQuantity());

            workOrderItem.setUnitWeight(x.getActualUnitWeight());
            workOrderItem.setTotalWeight(x.getActualTotalWeight());

            workOrderItem.setItemId(x.getItemId());

            workOrderItem.setOrderItemId(x.getOrderItemId());

            return workOrderItem;

        }).collect(Collectors.toList()));

        Iterable<ItemDto> assemblyItems = itemService.listItemsByType(ItemType.ASSEMBLY_ITEM, new String[]{"attributes", "billsOfMaterials"});
        String jsonAssemblyItems = objectMapper.writeValueAsString(assemblyItems);

        Iterable<ItemDto> inventoryItems = itemService.listItemsByType(ItemType.INVENTORY_ITEM, new String[]{"billsOfMaterials"});
        String jsonInventoryItems = objectMapper.writeValueAsString(inventoryItems);

        Iterable<ItemDto> expenseItems = itemService.listItemsByType(ItemType.EXPENSE_ITEM, new String[]{"billsOfMaterials"});
        String jsonExpenseItems = objectMapper.writeValueAsString(expenseItems);

        String jsonWorkOrder = objectMapper.writeValueAsString(workOrderDto);
        String jsonOrder = objectMapper.writeValueAsString(order);
        String jsonEstimate = objectMapper.writeValueAsString(estimate);

        model.addAttribute("mode", "new");
        model.addAttribute("workOrder", jsonWorkOrder);
        model.addAttribute("order", jsonOrder);
        model.addAttribute("estimate", jsonEstimate);

        model.addAttribute("assemblyItems", jsonAssemblyItems);
        model.addAttribute("inventoryItems", jsonInventoryItems);
        model.addAttribute("expenseItems", jsonExpenseItems);

        return "edit-workorder";
    }

    @RequestMapping(value = "/workorders/print/itemproductionsheet", method = RequestMethod.GET)
    public String itemProductionSheet() {

        return "prints/item-production-sheet";
    }
}