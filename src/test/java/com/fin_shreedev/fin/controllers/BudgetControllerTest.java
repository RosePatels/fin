package com.fin_shreedev.fin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fin_shreedev.fin.entities.Budget;
import com.fin_shreedev.fin.repositories.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetRepository budgetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBudgets() throws Exception {
        Mockito.when(budgetRepository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/budgets/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetBudgetByIdNotFound() throws Exception {
        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/budgets/read/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Budget not found"));
    }

    @Test
    void testCreateBudgetBadRequest() throws Exception {
        Budget budget = new Budget();
        budget.setBudgetName("");
        budget.setBudgetAmount(-1.0);
        budget.setAmountSpent(0.0);

        mockMvc.perform(post("/api/budgets/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(budget)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testCreateBudgetSuccess() throws Exception {
        Budget budget = new Budget();
        budget.setBudgetName("Groceries");
        budget.setBudgetAmount(200.0);
        budget.setAmountSpent(50.0);

        Mockito.when(budgetRepository.save(Mockito.any(Budget.class))).thenReturn(budget);

        mockMvc.perform(post("/api/budgets/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(budget)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.budgetName").value("Groceries"));
    }

    @Test
    void testUpdateBudgetNotFound() throws Exception {
        Budget budget = new Budget();
        budget.setBudgetName("Groceries");
        budget.setBudgetAmount(200.0);
        budget.setAmountSpent(50.0);

        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/budgets/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(budget)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Budget not found"));
    }

    @Test
    void testDeleteBudgetNotFound() throws Exception {
        Mockito.when(budgetRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/budgets/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Budget not found"));
    }
}