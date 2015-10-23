package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.*;
import com.aabplastic.backoffice.models.dto.EstimateDto;
import com.aabplastic.backoffice.models.dto.OrderDto;
import com.aabplastic.backoffice.models.dto.WorkOrderDto;
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
    public Order create(Order order) {

        Date now = new Date();

        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        final Order finalOrder = order;
        order.getItems().stream().forEach(x -> {
            x.setOrder(finalOrder);
            x.setCreatedAt(now);
            x.setUpdatedAt(now);
        });

        order = orderRepository.save(order);
        return order;
    }

    @Override
    public Order update(long id, Order putOrder) {

        List<OrderItem> putOrderItems = putOrder.getItems();
        Map<Long, OrderItem> map1 = putOrderItems.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        Date now = new Date();

        final Order order = orderRepository.findOne(id);
        List<OrderItem> orderItems = order.getItems();
        Map<Long, OrderItem> map2 = orderItems.stream().collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        Set<Long> ids = new HashSet<Long>();


        // Remove items
        orderItems.stream()
                .filter(x -> !map1.containsKey(x.getId()))
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .forEach(y -> {
                    order.getItems().remove(y);

                    ids.add(y.getId());
                });

        // Add new items
        putOrderItems.stream().filter(x -> !map2.containsKey(x.getId())).forEach(y -> {
            order.getItems().add(y);
            y.setOrder(order);
            y.setCreatedAt(now);
            y.setUpdatedAt(now);

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
                    y.setHandleLength(putOrderItem.getHandleLength());
                    y.setHandleRatio(putOrderItem.getHandleRatio());
                    y.setHandleWidth(putOrderItem.getHandleWidth());
                    y.setEmboss(putOrderItem.getEmboss());
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

        order.setCustomerId(putOrder.getCustomerId());
        order.setOrderDate(putOrder.getOrderDate());
        order.setEstimatedTimeOfArrival(putOrder.getEstimatedTimeOfArrival());
        order.setEstimatedTimeOfDeparture(putOrder.getEstimatedTimeOfDeparture());

        order.setUpdatedAt(now);

        order.setTotal(putOrder.getTotal());

        Order updatedOrder = orderRepository.save(order);

        return updatedOrder;
    }



    @Override
    public Iterable<Order> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return orders;
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
            x.getMaterials().forEach(y -> {
                y.setId(0);
                y.setEstimateItem(x);
            });

            x.getExpenses().forEach(y -> {
                y.setId(0);
                y.setEstimateItem(x);
            });
        });

        estimate = estimateRepository.save(estimate);

        return mapperFactory.getMapperFacade().map(estimate, EstimateDto.class);
    }

    @Override
    public EstimateDto updateEstimate(long id, EstimateDto estimateDto) {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        Estimate putEstimate = mapperFactory.getMapperFacade().map(estimateDto, Estimate.class);

        List<EstimateItem> putEstimateItems = putEstimate.getItems();
        Map<Long, EstimateItem> map1 = putEstimateItems.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(EstimateItem::getId, Function.identity()));

        Estimate estimate = estimateRepository.findOne(id);
        List<EstimateItem> estimateItems = estimate.getItems();

        estimateItems.stream().forEach(estimateItem -> {

            EstimateItem putEstimateItem = map1.get(estimateItem.getId());

            estimateItem.setActualPricePerWeightUnit(putEstimateItem.getActualPricePerWeightUnit());
            estimateItem.setActualThickness(putEstimateItem.getActualThickness());
            estimateItem.setActualTotalWeight(putEstimateItem.getActualTotalWeight());
            estimateItem.setActualUnitWeight(putEstimateItem.getActualUnitWeight());
            estimateItem.setBlowingWidth(putEstimateItem.getBlowingWidth());

            List<EstimateItemMaterial> putMaterials = putEstimateItem.getMaterials();
            Map<Long, EstimateItemMaterial> map11 = putMaterials.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(EstimateItemMaterial::getId, Function.identity()));

            List<EstimateItemMaterial> savedMaterials = estimateItem.getMaterials();
            Map<Long, EstimateItemMaterial> map22 = savedMaterials.stream().collect(Collectors.toMap(EstimateItemMaterial::getId, Function.identity()));

            Set<Long> ids = new HashSet<>();

            // Remove items
            savedMaterials.stream()
                    .filter(x -> !map11.containsKey(x.getId()))
                    .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                    .forEach(x -> {
                        estimateItem.getMaterials().remove(x);

                        ids.add(x.getId());
                    });

            // Add new items
            putMaterials.stream().filter(x -> !map22.containsKey(x.getId())).forEach(x -> {
                estimateItem.getMaterials().add(x);
                x.setEstimateItem(estimateItem);
                ids.add(x.getId());
            });

            // Update items
            savedMaterials.stream()
                    .filter(x -> !ids.contains(x.getId()))
                    .forEach(x -> {
                        EstimateItemMaterial estimateItemMaterial = map11.get(x.getId());

                        x.setQuantity(estimateItemMaterial.getQuantity());
                        x.setCostPerWeightUnit(estimateItemMaterial.getCostPerWeightUnit());
                        x.setTotal(estimateItemMaterial.getTotal());
                        x.setItemId(estimateItemMaterial.getItemId());

                    });

            estimateItem.setGusset(putEstimateItem.getGusset());
            estimateItem.setHandleRatio(putEstimateItem.getHandleRatio());
            estimateItem.setLength(putEstimateItem.getLength());
            estimateItem.setPricePerWeightUnit(putEstimateItem.getPricePerWeightUnit());
            estimateItem.setQuantity(putEstimateItem.getQuantity());
            estimateItem.setThickness(putEstimateItem.getThickness());
            estimateItem.setTotal(putEstimateItem.getTotal());
            estimateItem.setTotalCost(putEstimateItem.getTotalCost());
            estimateItem.setTotalWeight(putEstimateItem.getTotalWeight());
            estimateItem.setUnitWeight(putEstimateItem.getUnitWeight());
            estimateItem.setWidth(putEstimateItem.getWidth());
        });

        Date now = new Date();
        estimate.setUpdatedAt(now);

        Estimate updatedEstimate = estimateRepository.save(estimate);

        return mapperFactory.getMapperFacade().map(updatedEstimate, EstimateDto.class);
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

    @Override
    public WorkOrderDto getWorkOrderByOrderId(long orderId) {
        return null;
    }

    @Override
    public WorkOrderDto createWorkOrder(WorkOrderDto workOrderDto) {
        return null;
    }
}
