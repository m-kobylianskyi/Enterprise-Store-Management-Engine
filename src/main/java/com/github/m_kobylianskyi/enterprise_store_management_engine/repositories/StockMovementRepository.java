package com.github.m_kobylianskyi.enterprise_store_management_engine.repositories;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement,Long> {
    public List<Long> findByProductIdOrderByCreatedAtDesc(Long productId);
}
