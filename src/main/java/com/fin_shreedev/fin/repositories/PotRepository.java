package com.fin_shreedev.fin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fin_shreedev.fin.entities.Pot;

public interface PotRepository extends JpaRepository<Pot, Long> {
    
}
