package com.github.m_kobylianskyi.enterprise_store_management_engine.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @Column(name = "product_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @MapsId
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @Column(nullable = false,precision = 14,scale = 3)
    private BigDecimal quantity = BigDecimal.ZERO;
    @Column(name = "reorder_level",nullable = false,precision = 14,scale = 3)
    private BigDecimal reorderLevel = BigDecimal.ZERO;
    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(BigDecimal reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
