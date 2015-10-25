package com.aabplastic.backoffice.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "estimate_item_materials")
@Entity
public class EstimateItemMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "cost_per_weight_unit", nullable = false)
    private double costPerWeightUnit;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "material_id")
    private long materialId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false, insertable = false, updatable = false)
    private Material item;

    @JsonIgnore
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

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public Material getItem() {
        return item;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    public EstimateItem getEstimateItem() {
        return estimateItem;
    }

    public void setEstimateItem(EstimateItem estimateItem) {
        this.estimateItem = estimateItem;
    }
}
