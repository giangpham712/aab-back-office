package com.aabplastic.backoffice.models.dto;

import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemAttributeDto {

    private long id;

    private String attributeKey;

    private String attributeName;

    private String attributeValue;

    private ItemType type;

    @JsonIgnore
    private Item item;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
