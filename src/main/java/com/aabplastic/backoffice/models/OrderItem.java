package com.aabplastic.backoffice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "thickness", nullable = false)
    private double thickness;

    @Column(name = "blowing_width", nullable = false)
    private double blowingWidth;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "length", nullable = false)
    private double length;

    @Column(name = "emboss", nullable = false)
    private String emboss;

    @Column(name = "handle_length", nullable = false)
    private double handleLength;

    @Column(name = "handle_width", nullable = false)
    private double handleWidth;

    @Column(name = "handle_ratio", nullable = false)
    private double handleRatio;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "film_color", nullable = false)
    private String filmColor;

    @Column(name = "bag_type", nullable = false)
    private String bagType;

    @Column(name = "printing", nullable = false)
    private String printing;

    @Column(name = "outerbag_printing", nullable = false)
    private String outerbagPrinting;

    @Column(name = "pieces_per_outerbag", nullable = false)
    private int piecesPerOuterbag;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "pieces_per_carton", nullable = false)
    private int piecesPerCarton;

    @Column(name = "total_carton", nullable = false)
    private int totalCartons;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    // Constructor

    public OrderItem () {

    }

    // Setters and getters

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

    public String getEmboss() {
        return emboss;
    }

    public void setEmboss(String emboss) {
        this.emboss = emboss;
    }

    public double getHandleLength() {
        return handleLength;
    }

    public void setHandleLength(double handleLength) {
        this.handleLength = handleLength;
    }

    public double getHandleWidth() {
        return handleWidth;
    }

    public void setHandleWidth(double handleWidth) {
        this.handleWidth = handleWidth;
    }

    public double getHandleRatio() {
        return handleRatio;
    }

    public void setHandleRatio(double handleRatio) {
        this.handleRatio = handleRatio;
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

    public int getPiecesPerOuterbag() {
        return piecesPerOuterbag;
    }

    public void setPiecesPerOuterbag(int piecesPerOuterbag) {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
