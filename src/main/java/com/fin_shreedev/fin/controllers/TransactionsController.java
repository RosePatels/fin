package com.fin_shreedev.fin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fin_shreedev.fin.classes.ApiResponse;
import com.fin_shreedev.fin.entities.Transaction;
import com.fin_shreedev.fin.repositories.TransactionRepository;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("http://localhost:5173")
public class TransactionsController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/list")
    public List<Transaction> getAllTransactions(@RequestParam(value = "limit", required = false) Integer limit) {
        List<Transaction> allTransactions = transactionRepository.findAll();
        if (limit != null && limit > 0 && limit < allTransactions.size()) {
            return allTransactions.subList(0, limit);
        }
        return allTransactions;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        if(transaction.getTransactionName() == null || transaction.getTransactionName().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Transaction Name cannot be empty")
            );
        }

        if(transaction.getTransactionAmount() == null) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Transaction Amount cannot be null")
            );
        }

        if(transaction.getTransactionDate() == null) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Transaction Date cannot be empty")
            );
        }

        if(transaction.getCategoryType() == null || transaction.getCategoryType().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Category Type cannot be empty")
            );
        }

        if(transaction.getCategoryId() == null || transaction.getCategoryId() < 0) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Category ID cannot be empty or negative")
            );
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionName(transaction.getTransactionName());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTransactionDate(transaction.getTransactionDate());
        newTransaction.setCategoryType(transaction.getCategoryType());
        newTransaction.setCategoryId(transaction.getCategoryId());

        return ResponseEntity.ok(transactionRepository.save(newTransaction));
    }
}
