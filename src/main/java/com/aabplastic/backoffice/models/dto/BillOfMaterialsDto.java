package com.aabplastic.backoffice.models.dto;

import java.util.Date;
import java.util.List;

public class BillOfMaterialsDto {

    private long id;

    private String name;

    private List<BillOfMaterialsItemDto> items;

    private long itemId;

    private Date createdAt;

    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BillOfMaterialsItemDto> getItems() {
        return items;
    }

    public void setItems(List<BillOfMaterialsItemDto> items) {
        this.items = items;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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
}
