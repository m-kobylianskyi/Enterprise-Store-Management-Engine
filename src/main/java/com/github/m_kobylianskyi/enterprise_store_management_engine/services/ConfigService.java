package com.github.m_kobylianskyi.enterprise_store_management_engine.services;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.SimConfig;
import com.github.m_kobylianskyi.enterprise_store_management_engine.repositories.SimConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ConfigService {
    private final SimConfigRepository simConfigRepository;

    public ConfigService(SimConfigRepository simConfigRepository) {
        this.simConfigRepository = simConfigRepository;
    }
    private SimConfig getConfig(String key){
        return simConfigRepository.findById(key).orElseThrow(()-> new IllegalStateException("Config key '" + key + "' not found, check V2__seed.sql"));
    }
    @Transactional(readOnly = true)
    public String getString(String key){
        return getConfig(key).getValue();
    }
    @Transactional(readOnly = true)
    public int getInt(String key){
        String value = getString(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e){
            throw new IllegalStateException("Config key '" + key + "' has non-integer value" + value,e);
        }
    }
    @Transactional(readOnly = true)
    public BigDecimal getDecimal(String key){
        String value = getString(key);
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e){
            throw new IllegalStateException("Config key '" + key + "' has non-decimal value" + value,e);
        }
    }
}
