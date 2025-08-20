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
import com.fin_shreedev.fin.entities.Pot;
import com.fin_shreedev.fin.repositories.PotRepository;

@RestController
@RequestMapping("/api/pots")
@CrossOrigin("http://localhost:5173")
public class PotController {
    @Autowired
    private PotRepository potRepository;
    
   @GetMapping("/list")
    public List<Pot> getAllPots() {
        return potRepository.findAll();
    }

    @GetMapping("read/{id}")
    public ResponseEntity<?> getPotById(@PathVariable Long id) {
        return potRepository.findById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(
                new ApiResponse(false, "Pot not found")
            ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPot(@RequestBody Pot pot) {
        if (pot.getPotName() == null || pot.getPotName().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Pot Name cannot be empty")
            );
        }
        if (pot.getTotalSaved() == null || pot.getTotalSaved() < 0) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Total Saved cannot be empty")
            );
        }
        Pot newPot = new Pot();
        newPot.setPotName(pot.getPotName());
        newPot.setTotalSaved(pot.getTotalSaved());
        newPot.setTargetAmount(pot.getTargetAmount());
        return ResponseEntity.ok(potRepository.save(newPot));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updatePot(@PathVariable Long id, @RequestBody Pot potRequest) {
        Pot pot = potRepository.findById(id).orElse(null);
        if (pot == null) {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "Pot not found")
            );
        }
        if (potRequest.getPotName() == null || potRequest.getPotName().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Pot Name cannot be empty")
            );
        }
        if (potRequest.getTotalSaved() == null || potRequest.getTotalSaved() < 0) {
            return ResponseEntity.badRequest().body(
                new ApiResponse(false, "Total Saved cannot be empty")
            );
        }
        pot.setPotName(potRequest.getPotName());
        pot.setTotalSaved(potRequest.getTotalSaved());
        pot.setTargetAmount(potRequest.getTargetAmount());
        return ResponseEntity.ok(potRepository.save(pot));
    }   

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePot(@PathVariable Long id) {
        if (!potRepository.existsById(id)) {
            return ResponseEntity.status(404).body(
                new ApiResponse(false, "Pot not found")
            );
        }
        potRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Pot deleted"));
    }
}
