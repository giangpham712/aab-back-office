package com.aabplastic.backoffice.excel.models;

import com.aabplastic.backoffice.models.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductionSheetOrderItem {

    @JsonIgnore
    private ProductionSheetOrder productionSheetOrder;

    private String thickness;

    private String width;

    private String blowingWidth;

    private String length;
    private String gusset;
    private String weightPerLengthUnit;
    private String weightPerRoll;

    private String totalRolls;
    private String totalBlowingWeight;
    private String lengthPerRoll;
    private String productId;
    private String productName;

    private String emboss;
    private String totalWeight;

    private int[] rollNumbers;

    private Order order;

    public void setProductionSheetOrder(ProductionSheetOrder productionSheetOrder) {
        this.productionSheetOrder = productionSheetOrder;
    }

    public ProductionSheetOrder getProductionSheetOrder() {
        return productionSheetOrder;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getBlowingWidth() {
        return blowingWidth;
    }

    public void setBlowingWidth(String blowingWidth) {
        this.blowingWidth = blowingWidth;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setGusset(String gusset) {
        this.gusset = gusset;
    }

    public String getGusset() {
        return gusset;
    }

    public void setWeightPerLengthUnit(String weightPerLengthUnit) {
        this.weightPerLengthUnit = weightPerLengthUnit;
    }

    public String getWeightPerLengthUnit() {
        return weightPerLengthUnit;
    }

    public void setWeightPerRoll(String weightPerRoll) {
        this.weightPerRoll = weightPerRoll;
    }

    public String getWeightPerRoll() {
        return weightPerRoll;
    }

    public String getTotalRolls() {
        return totalRolls;
    }

    public void setTotalRolls(String totalRolls) {
        this.totalRolls = totalRolls;
    }

    public String getTotalBlowingWeight() {
        return totalBlowingWeight;
    }

    public void setTotalBlowingWeight(String totalBlowingWeight) {
        this.totalBlowingWeight = totalBlowingWeight;
    }

    public void setLengthPerRoll(String lengthPerRoll) {
        this.lengthPerRoll = lengthPerRoll;
    }

    public String getLengthPerRoll() {
        return lengthPerRoll;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getEmboss() {
        return emboss;
    }

    public void setEmboss(String emboss) {
        this.emboss = emboss;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int[] getRollNumbers() {
        return rollNumbers;
    }

    public void setRollNumbers(int[] rollNumbers) {
        this.rollNumbers = rollNumbers;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}


