package com.fin_shreedev.fin.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_name", nullable = false)
    private String budgetName;

    @Column(name = "budget_amount", nullable = false)
    private Double budgetAmount;

    @Column(name = "amount_spent", nullable = false)
    private Double amountSpent;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    } 

    public String getBudgetName() {
        return budgetName;
    }   

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Double getAmountSpent() {
        return amountSpent;
    } 

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }


}
