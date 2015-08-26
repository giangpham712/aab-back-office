package com.aabplastic.backoffice.models.dto;

import com.aabplastic.backoffice.models.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class EstimateDto {

    private long id;

    private String orderNumber;

    private Date createdAt;

    private Date updatedAt;

    private long orderId;

    private List<EstimateItemDto> items;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<EstimateItemDto> getItems() {
        return items;
    }

    public void setItems(List<EstimateItemDto> items) {
        this.items = items;
    }

}
