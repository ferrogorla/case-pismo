package com.pismo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "operation_types")
public class OperationType {

    @Id
    @Column(name = "operation_type_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "negative", nullable = false)
    private boolean negative;

    public OperationType() {
    }

    public OperationType(Long id, String description, boolean negative) {
        this.id = id;
        this.description = description;
        this.negative = negative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public BigDecimal getOperationSign() {
        return negative ? BigDecimal.valueOf(-1D) : BigDecimal.valueOf(1D);
    }

    public boolean isPayment() {
        return id.equals(4L);
    }

}