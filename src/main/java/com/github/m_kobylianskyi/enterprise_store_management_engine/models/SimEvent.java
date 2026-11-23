package com.github.m_kobylianskyi.enterprise_store_management_engine.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "sim_event",indexes = {@Index(name = "idx_sim_event_time", columnList = "sim_time"), @Index(name = "idx_sim_event_type_time", columnList = "type, sim_time")})
public class SimEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false,length = 30)
    private SimEventType type;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String payload;
    @Column(name = "sim_time",nullable = false)
    private LocalDateTime simTime;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SimEventType getType() {
        return type;
    }

    public void setType(SimEventType type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getSimTime() {
        return simTime;
    }

    public void setSimTime(LocalDateTime simTime) {
        this.simTime = simTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
