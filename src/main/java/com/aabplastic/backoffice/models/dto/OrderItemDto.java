package com.aabplastic.backoffice.models.dto;

import com.aabplastic.backoffice.models.Order;

import javax.persistence.*;
import java.util.Date;

//TODO Apply validation
public class OrderItemDto {

    private long id;

    private double thickness;

    private double blowingWidth;

    private double width;

    private double length;

    private String material;

    private String filmColor;

    private String bagType;

    private String printing;

    private String outerbagPrinting;

    private String piecesPerOuterbag;

    private int quantity;

    private int piecesPerCarton;

    private int totalCartons;

    private double unitPrice;

    private double total;

    private Date createdAt;

    private Date updatedAt;

    private long itemId;

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

    public String getBagType() {
        return bagType;
    }

    public void setBagType(String bagType) {
        this.bagType = bagType;
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
