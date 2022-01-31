package com.example.coffeemachine.repository;

import com.example.coffeemachine.entity.AvailableIngredientVolume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableIngredientVolumeRepository extends JpaRepository<AvailableIngredientVolume, Long> {

    Optional<AvailableIngredientVolume> findAvailableIngredientVolumeByName(String name);

}
