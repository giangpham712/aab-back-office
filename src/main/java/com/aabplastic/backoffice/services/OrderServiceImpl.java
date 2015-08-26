package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Estimate;
import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.OrderItem;
import com.aabplastic.backoffice.models.dto.EstimateDto;
import com.aabplastic.backoffice.models.dto.OrderDto;
import com.aabplastic.backoffice.repositories.EstimateRepository;
import com.aabplastic.backoffice.repositories.OrderRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Override
    public OrderDto create(OrderDto orderDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Order order = mapperFactory.getMapperFacade().map(orderDto, Order.class);

        Date now = new Date();

        order.setCustomerId(orderDto.getCustomerId());
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        final Order finalOrder = order;
        order.getItems().stream().forEach(x -> {
            x.setOrder(finalOrder);
            x.setCreatedAt(now);
            x.setUpdatedAt(now);
        });

        order = orderRepository.save(order);
        return mapperFactory.getMapperFacade().map(order, OrderDto.class);
    }

    @Override
    public OrderDto update(long id, OrderDto orderDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Order putOrder = mapperFactory.getMapperFacade().map(orderDto, Order.class);
        List<OrderItem> putOrderItems = putOrder.getItems();
        Map<Long, OrderItem> map1 = putOrderItems.stream().collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        Date now = new Date();

        final Order order = orderRepository.findOne(id);
        List<OrderItem> orderItems = order.getItems();
        Map<Long, OrderItem> map2 = orderItems.stream().collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        Set<Long> ids = new HashSet<Long>();

        // Add new items
        putOrderItems.stream().filter(x -> !map2.containsKey(x.getId())).forEach(y -> {
            order.getItems().add(y);
            y.setOrder(order);
            y.setCreatedAt(now);
            y.setUpdatedAt(now);

            ids.add(y.getId());
        });

        // Remove items
        orderItems.stream()
                .filter(x -> !map1.containsKey(x.getId()))
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .forEach(y -> {
                    order.getItems().remove(y);

                    ids.add(y.getId());
                });

        // Update items
        orderItems.stream()
                .filter(x -> !ids.contains(x.getId()))
                .forEach(y -> {
                    y.setUpdatedAt(now);

                    OrderItem putOrderItem = map1.get(y.getId());

                    y.setBagType(putOrderItem.getBagType());
                    y.setBlowingWidth(putOrderItem.getBlowingWidth());
                    y.setFilmColor(putOrderItem.getFilmColor());
                    y.setLength(putOrderItem.getLength());
                    y.setMaterial(putOrderItem.getMaterial());
                    y.setOuterbagPrinting(putOrderItem.getOuterbagPrinting());
                    y.setPiecesPerCarton(putOrderItem.getPiecesPerCarton());
                    y.setPiecesPerOuterbag(putOrderItem.getPiecesPerOuterbag());
                    y.setPrinting(putOrderItem.getPrinting());
                    y.setQuantity(putOrderItem.getQuantity());
                    y.setThickness(putOrderItem.getThickness());
                    y.setTotal(putOrderItem.getTotal());
                    y.setTotalCartons(putOrderItem.getTotalCartons());
                    y.setUnitPrice(putOrderItem.getUnitPrice());
                    y.setWidth(putOrderItem.getWidth());

                });

        order.setCustomerId(orderDto.getCustomerId());
        order.setOrderDate(orderDto.getOrderDate());
        order.setEstimatedTimeOfArrival(orderDto.getEstimatedTimeOfArrival());
        order.setEstimatedTimeOfDeparture(orderDto.getEstimatedTimeOfDeparture());

        order.setUpdatedAt(now);

        Order updatedOrder = orderRepository.save(order);

        return mapperFactory.getMapperFacade().map(updatedOrder, OrderDto.class);
    }

    @Override
    public Iterable<OrderDto> getAllOrders() {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Order.class, OrderDto.class)
                .exclude("items")
                .byDefault()
                .register();

        List<Order> orders = orderRepository.findAll();
        List<OrderDto> result = mapperFactory.getMapperFacade().mapAsList(orders, OrderDto.class);

        return result;
    }

    @Override
    public OrderDto getOrderById(long id) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        Order order = orderRepository.findOne(id);

        OrderDto result = mapperFactory.getMapperFacade().map(order, OrderDto.class);

        return result;
    }

    @Override
    public EstimateDto createEstimate(EstimateDto estimateDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Estimate estimate = mapperFactory.getMapperFacade().map(estimateDto, Estimate.class);

        Date now = new Date();

        estimate.setCreatedAt(now);
        estimate.setUpdatedAt(now);

        final Estimate finalEstimate = estimate;
        estimate.getItems().forEach(x -> {
            x.setEstimate(finalEstimate);
            x.setId(0);
            x.getCostItems().forEach(y -> {
                y.setId(0);
                y.setEstimateItem(x);
            });
        });

        estimate = estimateRepository.save(estimate);

        return mapperFactory.getMapperFacade().map(estimate, EstimateDto.class);
    }

    @Override
    public EstimateDto getEstimateById(long id) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        Estimate estimate = estimateRepository.findOne(id);
        EstimateDto result = mapperFactory.getMapperFacade().map(estimate, EstimateDto.class);

        return result;
    }

    @Override
    public EstimateDto getEstimateByOrderId(long orderId) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        List<Estimate> estimates = estimateRepository.findByOrderId(orderId);

        if (estimates.size() == 0) {
            return null;
        }

        Estimate estimate = estimates.get(estimates.size() - 1);
        mapperFactory.classMap(Estimate.class, EstimateDto.class)
                .exclude("order")
                .exclude("items");

        EstimateDto estimateDto = mapperFactory.getMapperFacade().map(estimate, EstimateDto.class);
        return estimateDto;
    }
}
