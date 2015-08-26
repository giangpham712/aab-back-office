package com.aabplastic.backoffice.models;


import javax.persistence.*;

@Table(name = "estimate_item_cost_item")
@Entity
public class EstimateItemCostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "cost_per_weight_unit", nullable = false)
    private double costPerWeightUnit;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "item_id")
    private long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_item_id", nullable = false)
    private EstimateItem estimateItem;

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public EstimateItem getEstimateItem() {
        return estimateItem;
    }

    public void setEstimateItem(EstimateItem estimateItem) {
        this.estimateItem = estimateItem;
    }
}
