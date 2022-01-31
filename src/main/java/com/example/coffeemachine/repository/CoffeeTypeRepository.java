package com.example.coffeemachine.repository;

import com.example.coffeemachine.entity.CoffeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoffeeTypeRepository extends JpaRepository<CoffeeType, Long> {

    Optional<CoffeeType> findCoffeeTypeByName(String name);
}
