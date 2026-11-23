package com.github.m_kobylianskyi.enterprise_store_management_engine.services;

import com.github.m_kobylianskyi.enterprise_store_management_engine.models.SimClock;
import com.github.m_kobylianskyi.enterprise_store_management_engine.repositories.SimClockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SimClockService {
    private static final long CLOCK_ID = 1L;

    private final SimClockRepository simClockRepository;

    public SimClockService(SimClockRepository simClockRepository) {
        this.simClockRepository = simClockRepository;
    }
    private SimClock getClock(){
        return simClockRepository.findById(CLOCK_ID).orElseThrow(() -> new IllegalStateException("sim_clock row with id=1 not found, check V2__seed.sql"));
    }
    @Transactional(readOnly = true)
    public LocalDateTime now(){
        SimClock clock = getClock();
        return clock.getSimTime();
    }
    @Transactional
    public void advance(Duration duration){
        if(duration.isNegative()){
            throw new IllegalArgumentException("Duration must not be negative");
        }
        SimClock clock = getClock();
        clock.setSimTime(clock.getSimTime().plus(duration));
    }
    @Transactional
    public void start(){
        SimClock clock = getClock();
        clock.setRunning(true);
    }
    @Transactional
    public void stop(){
        SimClock clock = getClock();
        clock.setRunning(false);
    }
    @Transactional
    public void setSpeed(BigDecimal speed){
        if(speed.signum() <= 0){
            throw new IllegalArgumentException("Speed must be more than 0");
        }
        SimClock clock = getClock();
        clock.setSpeedMultiplier(speed);
    }
}
