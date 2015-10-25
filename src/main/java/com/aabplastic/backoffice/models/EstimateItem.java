package com.aabplastic.backoffice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "estimate_items")
public class EstimateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "thickness", nullable = false)
    private double thickness;

    @Column(name = "actual_thickness", nullable = false)
    private double actualThickness;

    @Column(name = "gusset", nullable = false)
    private double gusset;

    @Column(name = "blowing_width", nullable = false)
    private double blowingWidth;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "length", nullable = false)
    private double length;

    @Column(name = "emboss", nullable = true)
    private String emboss;

    @Column(name = "handle_length", nullable = true)
    private double handleLength;

    @Column(name = "handle_width", nullable = true)
    private double handleWidth;

    @Column(name = "handle_ratio", nullable = true)
    private double handleRatio;

    @Column(name = "unit_weight")
    private double unitWeight;

    @Column(name = "actual_unit_weight")
    private double actualUnitWeight;

    @Column(name = "total_weight")
    private double totalWeight;

    @Column(name = "actual_total_weight")
    private double actualTotalWeight;

    @Column(name = "price_per_weight_unit")
    private double pricePerWeightUnit;

    @Column(name = "actual_price_per_weight_unit")
    private double actualPricePerWeightUnit;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    @Column(name = "order_item_id", nullable = false)
    private long orderItemId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false, insertable = false, updatable = false)
    private OrderItem orderItem;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estimateItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateItemMaterial> materials;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estimateItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateItemExpense> expenses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getActualThickness() {
        return actualThickness;
    }

    public void setActualThickness(double actualThickness) {
        this.actualThickness = actualThickness;
    }

    public double getGusset() {
        return gusset;
    }

    public void setGusset(double gusset) {
        this.gusset = gusset;
    }

    public double getBlowingWidth() {
        return blowingWidth;
    }

    public void setBlowingWidth(double blowingWidth) {
        this.blowingWidth = blowingWidth;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getEmboss() {
        return emboss;
    }

    public void setEmboss(String emboss) {
        this.emboss = emboss;
    }

    public double getHandleLength() {
        return handleLength;
    }

    public void setHandleLength(double handleLength) {
        this.handleLength = handleLength;
    }

    public double getHandleWidth() {
        return handleWidth;
    }

    public void setHandleWidth(double handleWidth) {
        this.handleWidth = handleWidth;
    }

    public double getHandleRatio() {
        return handleRatio;
    }

    public void setHandleRatio(double handleRatio) {
        this.handleRatio = handleRatio;
    }

    public double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(double unitWeight) {
        this.unitWeight = unitWeight;
    }

    public double getActualUnitWeight() {
        return actualUnitWeight;
    }

    public void setActualUnitWeight(double actualUnitWeight) {
        this.actualUnitWeight = actualUnitWeight;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getActualTotalWeight() {
        return actualTotalWeight;
    }

    public void setActualTotalWeight(double actualTotalWeight) {
        this.actualTotalWeight = actualTotalWeight;
    }

    public double getPricePerWeightUnit() {
        return pricePerWeightUnit;
    }

    public void setPricePerWeightUnit(double pricePerWeightUnit) {
        this.pricePerWeightUnit = pricePerWeightUnit;
    }

    public double getActualPricePerWeightUnit() {
        return actualPricePerWeightUnit;
    }

    public void setActualPricePerWeightUnit(double actualPricePerWeightUnit) {
        this.actualPricePerWeightUnit = actualPricePerWeightUnit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Estimate getEstimate() {
        return estimate;
    }

    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }

    public List<EstimateItemMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<EstimateItemMaterial> materials) {
        this.materials = materials;
    }

    public List<EstimateItemExpense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<EstimateItemExpense> expenses) {
        this.expenses = expenses;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
