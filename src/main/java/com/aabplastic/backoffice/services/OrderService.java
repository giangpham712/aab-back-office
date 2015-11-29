package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Estimate;
import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface OrderService {
    Order create(Order order);

    Iterable<Order> listAllOrders();

    Page<Order> listOrders(String search, int page, int limit, String sortBy, Sort.Direction sortDirection);

    OrderDto getOrderById(long id);

    Order findOrderById(long id);

    Estimate createEstimate(Estimate estimate);

    Estimate updateEstimate(Estimate estimate);

    Estimate updateEstimate(long id, Estimate estimate);

    Estimate getEstimateById(long id);

    Estimate getEstimateByOrderId(long orderId);

    Order update(long savedOrder, Order putOrder);

    void delete(long id);

    void deleteEstimate(long id);

    void deleteEstimate(Estimate savedEstimate);
}
