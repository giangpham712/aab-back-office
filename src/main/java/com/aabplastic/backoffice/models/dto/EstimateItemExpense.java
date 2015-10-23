package com.aabplastic.backoffice.models.dto;


import com.aabplastic.backoffice.enums.ItemType;
import com.aabplastic.backoffice.models.EstimateItem;
import com.aabplastic.backoffice.models.Item;

import javax.persistence.*;

@Table(name = "estimate_item_expense")
@Entity
public class EstimateItemExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "item_id")
    private long itemId;

    @Column(name = "type")
    private ItemType type;

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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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
