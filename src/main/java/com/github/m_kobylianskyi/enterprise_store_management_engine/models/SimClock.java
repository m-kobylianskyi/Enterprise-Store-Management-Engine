package com.github.m_kobylianskyi.enterprise_store_management_engine.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sim_clock")
public class SimClock {
    @Id
    private Long id = 1L;
    @Column(name = "sim_time",nullable = false)
    private LocalDateTime simTime;
    @Column(name = "speed_multiplier",nullable = false,precision = 8,scale = 2)
    private BigDecimal speedMultiplier = BigDecimal.ONE;
    @Column(name = "is_running",nullable = false)
    private Boolean running = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSimTime() {
        return simTime;
    }

    public void setSimTime(LocalDateTime simTime) {
        this.simTime = simTime;
    }

    public BigDecimal getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(BigDecimal speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
