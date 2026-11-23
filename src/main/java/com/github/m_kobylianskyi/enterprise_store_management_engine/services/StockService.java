package com.github.m_kobylianskyi.enterprise_store_management_engine.services;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.*;
import com.github.m_kobylianskyi.enterprise_store_management_engine.repositories.ProductRepository;
import com.github.m_kobylianskyi.enterprise_store_management_engine.repositories.StockMovementRepository;
import com.github.m_kobylianskyi.enterprise_store_management_engine.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final SimClockService simClockService;

    public StockService(StockRepository stockRepository, StockMovementRepository stockMovementRepository, ProductRepository productRepository, SimClockService simClockService) {
        this.stockRepository = stockRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.simClockService = simClockService;
    }
    private Stock getStock(Long productId){
        return stockRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Stock for Product " + productId + " not found"));
    }
    private Stock createStock(Long productId){
        Stock stock = new Stock();
        stock.setProduct(getProduct(productId));
        return stockRepository.save(stock);
    }
    private Product getProduct(Long productId){
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product " + productId + " not found"));
    }
    private StockMovement buildMovement(Product product,MovementType movementType, BigDecimal quantity, RefType refType,Long refId){
        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setRefId(refId);
        stockMovement.setQuantity(quantity);
        stockMovement.setType(movementType);
        stockMovement.setRefType(refType);
        stockMovement.setCreatedAt(simClockService.now());
        return stockMovement;
    }
    @Transactional
    public void receivePurchase(Long productId, BigDecimal quantity, RefType refType,Long refId){
        if (quantity.signum() <=0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Stock stock = stockRepository.findById(productId).orElseGet(() -> createStock(productId));
        stockMovementRepository.save(buildMovement(stock.getProduct(), MovementType.PURCHASE, quantity, refType,refId));
        stock.setQuantity(stock.getQuantity().add(quantity));
    }
    @Transactional
    public void returnToStock(Long productId, BigDecimal quantity, RefType refType,Long refId){
        if (quantity.signum() <=0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Stock stock = stockRepository.findById(productId).orElseGet(() -> createStock(productId));
        stockMovementRepository.save(buildMovement(stock.getProduct(), MovementType.RETURN, quantity, refType,refId));
        stock.setQuantity(stock.getQuantity().add(quantity));
    }
    @Transactional
    public void sell(Long productId, BigDecimal quantity,Long refId){
        if (quantity.signum() <=0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Stock stock = getStock(productId);
        if (stock.getQuantity().compareTo(quantity) < 0) {
            throw new InsufficientStockException(productId);
        }
        stockMovementRepository.save(buildMovement(stock.getProduct(), MovementType.SALE, quantity.negate(), RefType.SALES_ORDER,refId));
        stock.setQuantity(stock.getQuantity().subtract(quantity));
    }
    @Transactional
    public void writeOff(Long productId, BigDecimal quantity){
        if (quantity.signum() <=0){
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Stock stock = getStock(productId);
        if (stock.getQuantity().compareTo(quantity) < 0) {
            throw new InsufficientStockException(productId);
        }
        stockMovementRepository.save(buildMovement(stock.getProduct(), MovementType.WRITE_OFF, quantity.negate(), null,null));
        stock.setQuantity(stock.getQuantity().subtract(quantity));
    }
    @Transactional
    public void adjust(Long productId, BigDecimal newQuantity){
        if (newQuantity.signum() < 0) {
            throw new IllegalArgumentException("Quantity must not be negative");
        }
        Stock stock = getStock(productId);
        BigDecimal diff = newQuantity.subtract(stock.getQuantity());
        if (diff.signum() == 0) {
            return;
        }
        stockMovementRepository.save(buildMovement(stock.getProduct(), MovementType.ADJUSTMENT, diff, null,null));
        stock.setQuantity(newQuantity);
    }
    @Transactional(readOnly = true)
    public BigDecimal getQuantity(Long productId){
        Stock stock = getStock(productId);
        return stock.getQuantity();
    }
}
