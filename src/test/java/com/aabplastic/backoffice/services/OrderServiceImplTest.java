package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Order;
import com.aabplastic.backoffice.models.OrderItem;
import com.aabplastic.backoffice.repositories.OrderRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest extends TestCase {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private List<Order> orders;

    private List<OrderItem> orderItems;

    @Before
    public void setUp() {

        Order order1 = new Order();

        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        List<OrderItem> order1Items = new ArrayList<>();
        order1Items.add(orderItem1);
        order1Items.add(orderItem2);

        order1.setItems(order1Items);

        orders = new ArrayList<>();
        orderItems = new ArrayList<>();


        orders.add(order1);

    }

    @Test
    public void testCreate_Success() throws Exception {

        when(orderRepository.save(any(Order.class))).thenReturn(orders.get(0));

        Order newOrder = new Order();

        Order actual = orderService.create(newOrder);

        verify(orderRepository).save(newOrder);

        assertEquals(newOrder.getCreatedAt(), newOrder.getUpdatedAt());

        for (OrderItem item : newOrder.getItems()) {
            assertEquals(newOrder, item.getOrder());
            assertEquals(item.getCreatedAt(), item.getUpdatedAt());

            assertEquals(newOrder.getCreatedAt(), item.getUpdatedAt());
            assertEquals(newOrder.getUpdatedAt(), item.getUpdatedAt());
        }

        assertEquals(orders.get(0), actual);
        assertEquals(orders.get(0).getItems().size(), actual.getItems().size());

    }

    public void testUpdate() throws Exception {

    }

    public void testGetAllOrders() throws Exception {

    }

    public void testGetOrderById() throws Exception {

    }

    public void testCreateEstimate() throws Exception {

    }

    public void testUpdateEstimate() throws Exception {

    }

    public void testGetEstimateById() throws Exception {

    }

    public void testGetEstimateByOrderId() throws Exception {

    }

    public void testGetWorkOrderByOrderId() throws Exception {

    }

    public void testCreateWorkOrder() throws Exception {

    }
}