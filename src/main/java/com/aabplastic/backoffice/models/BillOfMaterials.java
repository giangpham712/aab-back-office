package com.aabplastic.backoffice.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill_of_materials")
public class BillOfMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "bill")
    private List<BillOfMaterialsItem> items;

    @Column(name = "item_id", nullable = false)
    private long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "default_bom", nullable = false)
    private boolean defaultBOM;

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

    public List<BillOfMaterialsItem> getItems() {
        return items;
    }

    public void setItems(List<BillOfMaterialsItem> items) {
        this.items = items;
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

    public boolean isDefaultBOM() {
        return defaultBOM;
    }

    public void setDefaultBOM(boolean defaultBOM) {
        this.defaultBOM = defaultBOM;
    }
}
