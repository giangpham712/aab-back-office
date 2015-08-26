package com.aabplastic.backoffice.models.dto;

import com.aabplastic.backoffice.models.Customer;
import com.aabplastic.backoffice.models.OrderItem;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//TODO Apply validation
public class OrderDto {

    private long id;

    @NotBlank(message = "Order number must not be blank")
    private String orderNumber;

    private Date estimatedTimeOfArrival;

    @DateTimeFormat
    private Date estimatedTimeOfDeparture;

    private Date orderDate;

    private String port;

    private double total;

    private Date createdAt;


    private Date updatedAt;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemDto> items;

    private long customerId;

    private CustomerDto customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public void setEstimatedTimeOfArrival(Date estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public Date getEstimatedTimeOfDeparture() {
        return estimatedTimeOfDeparture;
    }

    public void setEstimatedTimeOfDeparture(Date estimatedTimeOfDeparture) {
        this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
