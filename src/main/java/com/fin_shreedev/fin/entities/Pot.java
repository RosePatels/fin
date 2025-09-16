package com.fin_shreedev.fin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pots")
public class Pot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "pot_name", nullable = false)
    private String potName;

    @Column(name = "total_saved", nullable = false)
    private Double totalSaved;

    @Column(name = "pot_target", nullable = false)
    private Double targetAmount;

    //Getters and Setters
    public Long getId()  {
        return id;
    }

    public void setId(Long id)  {
        this.id = id;
    }

    public String getPotName() {
        return potName;
    }

    public void setPotName(String potName)  {
        this.potName = potName;
    }

    public Double getTotalSaved()  {
        return totalSaved;
    }

    public void setTotalSaved(Double totalSaved) {
        this.totalSaved = totalSaved;
    }

    public Double getTargetAmount()   {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

}