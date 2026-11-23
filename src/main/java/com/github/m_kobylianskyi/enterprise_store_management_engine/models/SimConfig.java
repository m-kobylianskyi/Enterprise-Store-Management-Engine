package com.github.m_kobylianskyi.enterprise_store_management_engine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sim_config")
public class SimConfig {
    @Id
    @Column(name = "config_key",length = 100)
    private String key;
    @Column(name = "config_value",nullable = false,length = 500)
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
