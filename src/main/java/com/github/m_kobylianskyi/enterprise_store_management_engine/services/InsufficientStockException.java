package com.github.m_kobylianskyi.enterprise_store_management_engine.services;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId) {
        super("Insufficient stock for product " + productId);
    }
}