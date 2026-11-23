package com.github.m_kobylianskyi.enterprise_store_management_engine.repositories;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
