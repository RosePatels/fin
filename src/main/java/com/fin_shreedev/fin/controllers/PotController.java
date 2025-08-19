package com.fin_shreedev.fin.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public Optional<Pot> getPotById(Long id) {
        return Optional.ofNullable(potRepository.findById(id).orElse(null));
    }

    @PostMapping("/create")
    public Pot createPot(@RequestBody Pot pot) {
        Pot newPot = new Pot();
        newPot.setPotName(pot.getPotName());
        newPot.setTotalSaved(pot.getTotalSaved());
        newPot.setTargetAmount(pot.getTargetAmount());
        return potRepository.save(pot);
    }

    @PutMapping("/{id}")
    public Pot updatePot(@PathVariable Long id, @RequestBody Pot potRequest) {
        // if (potRepository.existsById(id)) {
            // pot.setId(id);
            Pot pot = potRepository.findById(id).orElseThrow(() -> new RuntimeException("Pot not found"));
            pot.setId(id);
            pot.setPotName(potRequest.getPotName());
            pot.setTotalSaved(potRequest.getTotalSaved());
            pot.setTargetAmount(potRequest.getTargetAmount());
            return potRepository.save(pot);
        // }
    }   

    @DeleteMapping("/{id}")
    public void deletePot(@PathVariable Long id) {
        potRepository.deleteById(id);
    }

    
}
