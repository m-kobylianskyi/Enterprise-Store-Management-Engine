package com.github.m_kobylianskyi.enterprise_store_management_engine.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll",uniqueConstraints = @UniqueConstraint(name = "uq_payroll_period", columnNames = {"employee_id", "period_start", "period_end"}))
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;
    @Column(name = "period_start",nullable = false)
    private LocalDate periodStart;
    @Column(name = "period_end",nullable = false)
    private LocalDate periodEnd;
    @Column(name = "hours_worked",nullable = false,precision = 8,scale = 2)
    private BigDecimal hoursWorked;
    @Column(name = "rate_snapshot",nullable = false,precision = 12,scale = 2)
    private BigDecimal rateSnapshot;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public BigDecimal getRateSnapshot() {
        return rateSnapshot;
    }

    public void setRateSnapshot(BigDecimal rateSnapshot) {
        this.rateSnapshot = rateSnapshot;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
