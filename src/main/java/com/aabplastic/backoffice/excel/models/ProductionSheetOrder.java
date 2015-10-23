package com.aabplastic.backoffice.excel.models;

import java.util.List;

public class ProductionSheetOrder {

    private String orderNumber;
    private String customer;
    private String orderName;

    private List<ProductionSheetOrderItem> productionSheetOrderItems;

    public ProductionSheetOrder() {

    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setProductionSheetOrderItems(List<ProductionSheetOrderItem> productionSheetOrderItems) {
        this.productionSheetOrderItems = productionSheetOrderItems;
    }

    public List<ProductionSheetOrderItem> getProductionSheetOrderItems() {
        return productionSheetOrderItems;
    }
}
