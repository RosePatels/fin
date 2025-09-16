package com.fin_shreedev.fin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fin_shreedev.fin.entities.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
}
