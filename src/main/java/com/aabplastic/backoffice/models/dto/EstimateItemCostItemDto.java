package com.aabplastic.backoffice.models.dto;

public class EstimateItemCostItemDto {

    private long id;

    private double quantity;

    private double costPerWeightUnit;

    private double total;

    private long itemId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCostPerWeightUnit() {
        return costPerWeightUnit;
    }

    public void setCostPerWeightUnit(double costPerWeightUnit) {
        this.costPerWeightUnit = costPerWeightUnit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
