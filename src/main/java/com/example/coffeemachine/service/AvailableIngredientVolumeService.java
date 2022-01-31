package com.example.coffeemachine.service;

import com.example.coffeemachine.DTO.AvailableIngredientVolumeDTO;
import com.example.coffeemachine.DTO.CoffeeOrderDTO;
import com.example.coffeemachine.entity.AvailableIngredientVolume;
import com.example.coffeemachine.entity.CoffeeType;
import com.example.coffeemachine.exception.IngredientNotFoundException;
import com.example.coffeemachine.exception.NotEnoughIngredientsVolumeException;
import com.example.coffeemachine.repository.AvailableIngredientVolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableIngredientVolumeService {

    private final AvailableIngredientVolumeRepository availableIngredientVolumeRepository;

    @Autowired
    public AvailableIngredientVolumeService(AvailableIngredientVolumeRepository availableIngredientVolumeRepository) {
        this.availableIngredientVolumeRepository = availableIngredientVolumeRepository;
    }

    public void reduceAllIngredientsVolume(CoffeeOrderDTO coffeeOrderDTO, CoffeeType coffeeType) {
        reduceIngredientVolume("sugar", coffeeOrderDTO.getSugar());
        reduceIngredientVolume("coffee", coffeeType.getCoffeeGrams());
        reduceIngredientVolume("milk",
                coffeeType.getMilkMilliliters() + coffeeType.getMilkFoamMilliliters());
        reduceIngredientVolume("water", coffeeType.getWaterMilliliters());
    }

    public List<AvailableIngredientVolume> increaseIngredientVolume(AvailableIngredientVolumeDTO[] availableIngredientVolumeDTOS) {

        List<AvailableIngredientVolume> availableIngredientVolumeList = new ArrayList<>();
        for (AvailableIngredientVolumeDTO availableIngredientVolumeDTO : availableIngredientVolumeDTOS) {
            AvailableIngredientVolume availableIngredientVolume = getAvailableIngredient(availableIngredientVolumeDTO
                    .getName());
            availableIngredientVolumeList.add(addIngredientVolume(availableIngredientVolumeDTO, availableIngredientVolume));
        }

        return availableIngredientVolumeList;
    }

    private AvailableIngredientVolume getAvailableIngredient(String name) {
        return availableIngredientVolumeRepository.findAvailableIngredientVolumeByName(name)
                .orElseThrow(() -> new IngredientNotFoundException(name + " not found"));
    }

    private AvailableIngredientVolume addIngredientVolume(AvailableIngredientVolumeDTO availableIngredientVolumeDTO,
                                                          AvailableIngredientVolume availableIngredientVolume) {

        availableIngredientVolume.setAvailableVolume(availableIngredientVolume.getAvailableVolume()
                + availableIngredientVolumeDTO.getAddedVolume());

        return availableIngredientVolumeRepository.save(availableIngredientVolume);
    }

    private void reduceIngredientVolume(String ingredientName, int ingredientVolumeInOrder) {
        AvailableIngredientVolume availableIngredientVolume = getAvailableIngredient(ingredientName);
        if (availableIngredientVolume.getAvailableVolume() - ingredientVolumeInOrder < 0)
            throw new NotEnoughIngredientsVolumeException("Sorry, not enough " + ingredientName);

        availableIngredientVolume.setAvailableVolume(availableIngredientVolume.getAvailableVolume()
                - ingredientVolumeInOrder);
        availableIngredientVolumeRepository.save(availableIngredientVolume);
    }

}
