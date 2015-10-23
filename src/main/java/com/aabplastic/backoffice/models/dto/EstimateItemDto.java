package com.aabplastic.backoffice.models.dto;

import javax.persistence.Column;
import java.util.List;

public class EstimateItemDto {

    private long id;

    private double thickness;

    private double actualThickness;

    private double gusset;

    private double blowingWidth;

    private double width;

    private double length;

    private double handleLength;

    private double handleWidth;

    private double handleRatio;

    private double unitWeight;

    private double actualUnitWeight;

    private double totalWeight;

    private double actualTotalWeight;

    private double pricePerWeightUnit;

    private double actualPricePerWeightUnit;

    private String material;

    private String filmColor;

    private String printing;

    private String outerbagPrinting;

    private String piecesPerOuterbag;

    private int quantity;

    private int piecesPerCarton;

    private int totalCartons;

    private double unitPrice;

    private double total;

    private double totalCost;

    private long itemId;

    private long orderItemId;

    private List<EstimateItemCostItemDto> costItems;

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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getFilmColor() {
        return filmColor;
    }

    public void setFilmColor(String filmColor) {
        this.filmColor = filmColor;
    }

    public String getPrinting() {
        return printing;
    }

    public void setPrinting(String printing) {
        this.printing = printing;
    }

    public String getOuterbagPrinting() {
        return outerbagPrinting;
    }

    public void setOuterbagPrinting(String outerbagPrinting) {
        this.outerbagPrinting = outerbagPrinting;
    }

    public String getPiecesPerOuterbag() {
        return piecesPerOuterbag;
    }

    public void setPiecesPerOuterbag(String piecesPerOuterbag) {
        this.piecesPerOuterbag = piecesPerOuterbag;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPiecesPerCarton() {
        return piecesPerCarton;
    }

    public void setPiecesPerCarton(int piecesPerCarton) {
        this.piecesPerCarton = piecesPerCarton;
    }

    public int getTotalCartons() {
        return totalCartons;
    }

    public void setTotalCartons(int totalCartons) {
        this.totalCartons = totalCartons;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public List<EstimateItemCostItemDto> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<EstimateItemCostItemDto> costItems) {
        this.costItems = costItems;
    }
}
