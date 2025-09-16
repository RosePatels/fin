package com.fin_shreedev.fin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fin_shreedev.fin.entities.Pot;
import com.fin_shreedev.fin.repositories.PotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PotController.class)
class PotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PotRepository potRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPots() throws Exception {
        Mockito.when(potRepository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/pots/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetPotByIdNotFound() throws Exception {
        Mockito.when(potRepository.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/pots/read/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Pot not found"));
    }

    @Test
    void testCreatePotBadRequest() throws Exception {
        Pot pot = new Pot();
        pot.setPotName("");
        pot.setTotalSaved(-1.0);
        pot.setTargetAmount(100.0);

        mockMvc.perform(post("/api/pots/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pot)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testCreatePotSuccess() throws Exception {
        Pot pot = new Pot();
        pot.setPotName("Vacation");
        pot.setTotalSaved(50.0);
        pot.setTargetAmount(100.0);

        Mockito.when(potRepository.save(Mockito.any(Pot.class))).thenReturn(pot);

        mockMvc.perform(post("/api/pots/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pot)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.potName").value("Vacation"));
    }

    @Test
    void testUpdatePotNotFound() throws Exception {
        Pot pot = new Pot();
        pot.setPotName("Vacation");
        pot.setTotalSaved(50.0);
        pot.setTargetAmount(100.0);

        Mockito.when(potRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/pots/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pot)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Pot not found"));
    }

    @Test
    void testDeletePotNotFound() throws Exception {
        Mockito.when(potRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/pots/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Pot not found"));
    }
}