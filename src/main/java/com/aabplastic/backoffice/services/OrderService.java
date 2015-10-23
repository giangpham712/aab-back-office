package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.dto.EstimateDto;
import com.aabplastic.backoffice.models.dto.OrderDto;
import com.aabplastic.backoffice.models.dto.WorkOrderDto;

public interface OrderService {
    Order create(Order order);

    Iterable<Order> getAllOrders();

    OrderDto getOrderById(long id);

    EstimateDto createEstimate(EstimateDto estimateDto);

    EstimateDto updateEstimate(long id, EstimateDto estimateDto);

    EstimateDto getEstimateById(long id);

    EstimateDto getEstimateByOrderId(long orderId);

    Order update(long savedOrder, Order putOrder);

    WorkOrderDto getWorkOrderByOrderId(long orderId);

    WorkOrderDto createWorkOrder(WorkOrderDto workOrderDto);
}
