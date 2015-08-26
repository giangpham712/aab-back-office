package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.dto.EstimateDto;
import com.aabplastic.backoffice.models.dto.OrderDto;

public interface OrderService {
    OrderDto create(OrderDto orderDto);

    Iterable<OrderDto> getAllOrders();

    OrderDto getOrderById(long id);

    EstimateDto createEstimate(EstimateDto estimateDto);

    EstimateDto getEstimateById(long id);

    EstimateDto getEstimateByOrderId(long orderId);

    OrderDto update(long savedOrder, OrderDto orderDto);
}
