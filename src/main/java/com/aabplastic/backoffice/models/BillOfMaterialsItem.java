package com.aabplastic.backoffice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bill_of_materials_item")
public class BillOfMaterialsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private BillOfMaterials bill;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "material_id", nullable = false)
    private long materialId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false, insertable = false, updatable = false)
    private Material material;

    public BillOfMaterialsItem() {}

    public BillOfMaterialsItem (long materialId, double quantity) {
        this.materialId = materialId;
        this.quantity = quantity;
    }

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

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
