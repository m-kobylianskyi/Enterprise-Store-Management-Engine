package com.github.m_kobylianskyi.enterprise_store_management_engine.repositories;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.SimClock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimClockRepository extends JpaRepository<SimClock,Long> {

}
