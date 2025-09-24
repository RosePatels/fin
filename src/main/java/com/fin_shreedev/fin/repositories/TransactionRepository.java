package com.fin_shreedev.fin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fin_shreedev.fin.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
