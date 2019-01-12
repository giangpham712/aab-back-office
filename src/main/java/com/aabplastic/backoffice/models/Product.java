package com.aabplastic.backoffice.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    // Product specifications

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "blowing_width")
    private double blowingWidth;

    @Column(name = "thickness")
    private double thickness;

    @Column(name = "gusset")
    private double gusset;

    @Column(name = "handle_width")
    private double handleWidth;

    @Column(name = "handle_length")
    private double handleLength;

    @Column(name = "material")
    private String material;

    @Column(name = "film_color")
    private String filmColor;

    @Column(name = "bag_type")
    private String bagType;

    @Column(name = "emboss")
    private String emboss;

    @Column(name = "pieces_per_outerbag")
    private int piecesPerOuterbag;

    @Column(name = "pieces_per_carton")
    private int piecesPerCarton;

    @Column(name = "printing")
    private String printing;

    @Column(name = "outerbagPrinting")
    private String outerbagPrinting;

    @Column(name = "default_bill_id")
    private long defaultBillOfMaterialsId;

    // Production

    @Column(name = "paint_per_unit_weight")
    private double paintPerUnitWeight;

    @Column(name = "master_batch_code")
    private String masterBatchCode;

    @Column(name = "master_batch_amount")
    private double masterBatchAmount;

    @Column(name = "ink_code")
    private String inkCode;

    @Column(name = "ink_amount")
    private double inkAmount;

    // Carton box
    @Column(name = "carton_box_layer")
    private double cartonBoxLayer;

    @Column(name = "carton_length")
    private double cartonLength;

    @Column(name = "carton_width")
    private double cartonWidth;

    @Column(name = "carton_height")
    private double cartonHeight;

    @Column(name = "recycle_rate")
    private double recycleRate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getBlowingWidth() {
        return blowingWidth;
    }

    public void setBlowingWidth(double blowingWidth) {
        this.blowingWidth = blowingWidth;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getGusset() {
        return gusset;
    }

    public void setGusset(double gusset) {
        this.gusset = gusset;
    }

    public double getHandleWidth() {
        return handleWidth;
    }

    public void setHandleWidth(double handleWidth) {
        this.handleWidth = handleWidth;
    }

    public double getHandleLength() {
        return handleLength;
    }

    public void setHandleLength(double handleLength) {
        this.handleLength = handleLength;
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

    public String getEmboss() {
        return emboss;
    }

    public void setEmboss(String emboss) {
        this.emboss = emboss;
    }

    public int getPiecesPerOuterbag() {
        return piecesPerOuterbag;
    }

    public void setPiecesPerOuterbag(int piecesPerOuterbag) {
        this.piecesPerOuterbag = piecesPerOuterbag;
    }

    public int getPiecesPerCarton() {
        return piecesPerCarton;
    }

    public void setPiecesPerCarton(int piecesPerCarton) {
        this.piecesPerCarton = piecesPerCarton;
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

    public long getDefaultBillOfMaterialsId() {
        return defaultBillOfMaterialsId;
    }

    public void setDefaultBillOfMaterialsId(long defaultBillOfMaterialsId) {
        this.defaultBillOfMaterialsId = defaultBillOfMaterialsId;
    }

    public double getPaintPerUnitWeight() {
        return paintPerUnitWeight;
    }

    public void setPaintPerUnitWeight(double paintPerUnitWeight) {
        this.paintPerUnitWeight = paintPerUnitWeight;
    }

    public String getMasterBatchCode() {
        return masterBatchCode;
    }

    public void setMasterBatchCode(String masterBatchCode) {
        this.masterBatchCode = masterBatchCode;
    }

    public double getMasterBatchAmount() {
        return masterBatchAmount;
    }

    public void setMasterBatchAmount(double masterBatchAmount) {
        this.masterBatchAmount = masterBatchAmount;
    }

    public String getInkCode() {
        return inkCode;
    }

    public void setInkCode(String inkCode) {
        this.inkCode = inkCode;
    }

    public double getInkAmount() {
        return inkAmount;
    }

    public void setInkAmount(double inkAmount) {
        this.inkAmount = inkAmount;
    }

    public double getCartonBoxLayer() {
        return cartonBoxLayer;
    }

    public void setCartonBoxLayer(double cartonBoxLayer) {
        this.cartonBoxLayer = cartonBoxLayer;
    }

    public double getCartonLength() {
        return cartonLength;
    }

    public void setCartonLength(double cartonLength) {
        this.cartonLength = cartonLength;
    }

    public double getCartonWidth() {
        return cartonWidth;
    }

    public void setCartonWidth(double cartonWidth) {
        this.cartonWidth = cartonWidth;
    }

    public double getCartonHeight() {
        return cartonHeight;
    }

    public void setCartonHeight(double cartonHeight) {
        this.cartonHeight = cartonHeight;
    }

    public double getRecycleRate() {
        return recycleRate;
    }

    public void setRecycleRate(double recycleRate) {
        this.recycleRate = recycleRate;
    }
}
