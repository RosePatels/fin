package com.fin_shreedev.fin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fin_shreedev.fin.classes.ApiResponse;
import com.fin_shreedev.fin.entities.Budget;
import com.fin_shreedev.fin.repositories.BudgetRepository;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin("http://localhost:5173")
public class BudgetController {
    @Autowired
    private BudgetRepository budgetRepository;

    @GetMapping("/list")
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> getBudgetById(@PathVariable Long id) {
        return budgetRepository.findById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(
                new ApiResponse(false, "Budget not found")
            ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBudget(@RequestBody Budget budget) {
        if (budget.getBudgetName() == null || budget.getBudgetName().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Budget Name cannot be empty")
            );
        }
        if (budget.getBudgetAmount() == null || budget.getBudgetAmount() < 0) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Budget Amount cannot be empty or negative")
            );
        }
        Budget newBudget = new Budget();
        newBudget.setBudgetName(budget.getBudgetName());
        newBudget.setBudgetAmount(budget.getBudgetAmount());
        newBudget.setAmountSpent(0.0); // Initialize amount spent to 0
        return ResponseEntity.ok(budgetRepository.save(newBudget));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable Long id, @RequestBody Budget budgetRequest) {
        Budget existingBudget = budgetRepository.findById(id).orElse(null);
        if (existingBudget == null) {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "Budget not found")
            );
        }

        if(budgetRequest.getBudgetName() == null || budgetRequest.getBudgetName().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Budget Name cannot be empty")
            );
        }

        if(budgetRequest.getBudgetAmount() == null || budgetRequest.getBudgetAmount() < 0) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Budget Amount cannot be empty or negative")
            );
        }

        existingBudget.setBudgetName(budgetRequest.getBudgetName());
        existingBudget.setBudgetAmount(budgetRequest.getBudgetAmount());
        // Note: Amount spent is not updated here to prevent accidental overwrites
        return ResponseEntity.ok(budgetRepository.save(existingBudget));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long id) {
        if (!budgetRepository.existsById(id)) {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "Budget not found")
            );
        }

        budgetRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Budget deleted"));
    }
}
    
