package com.aabplastic.backoffice.models;

import javax.persistence.*;

@Entity
@Table(name = "bill_of_materials_item")
public class BillOfMaterialsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private BillOfMaterials bill;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "item_id", nullable = false)
    private long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BillOfMaterials getBill() {
        return bill;
    }

    public void setBill(BillOfMaterials bill) {
        this.bill = bill;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
}
