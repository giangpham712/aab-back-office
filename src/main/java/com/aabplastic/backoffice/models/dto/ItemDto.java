package com.aabplastic.backoffice.models.dto;


import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.BillOfMaterials;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

public class ItemDto {

    private long id;

    private String code;

    private String itemNumber;

    private String displayName;

    private ItemType type;

    private String description;

    private boolean trackInventory;

    private int quantityOnHand;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;

    private List<ItemAttributeDto> attributes;

    private List<BillOfMaterialsDto> billsOfMaterials;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTrackInventory() {
        return trackInventory;
    }

    public void setTrackInventory(boolean trackInventory) {
        this.trackInventory = trackInventory;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public List<ItemAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public List<BillOfMaterialsDto> getBillsOfMaterials() {
        return billsOfMaterials;
    }

    public void setBillsOfMaterials(List<BillOfMaterialsDto> billsOfMaterials) {
        this.billsOfMaterials = billsOfMaterials;
    }
}
