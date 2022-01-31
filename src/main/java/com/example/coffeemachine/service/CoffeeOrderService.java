package com.example.coffeemachine.service;

import com.example.coffeemachine.DTO.CoffeeOrderDTO;
import com.example.coffeemachine.entity.CoffeeOrder;
import com.example.coffeemachine.entity.CoffeeType;
import com.example.coffeemachine.exception.CoffeeTypeNotFoundException;
import com.example.coffeemachine.repository.CoffeeOrderRepository;
import com.example.coffeemachine.repository.CoffeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CoffeeOrderService {

    private final CoffeeTypeRepository coffeeTypeRepository;
    private final CoffeeOrderRepository coffeeOrderRepository;
    private final AvailableIngredientVolumeService availableIngredientValueService;

    @Autowired
    public CoffeeOrderService(CoffeeTypeRepository coffeeTypeRepository,
                              CoffeeOrderRepository coffeeOrderRepository,
                              AvailableIngredientVolumeService availableIngredientValueService) {
        this.coffeeTypeRepository = coffeeTypeRepository;
        this.coffeeOrderRepository = coffeeOrderRepository;
        this.availableIngredientValueService = availableIngredientValueService;
    }

    @Transactional
    public CoffeeOrder createOrder(CoffeeOrderDTO coffeeOrderDTO) {

        CoffeeType coffeeType = getCoffeeType(coffeeOrderDTO);

        availableIngredientValueService.reduceAllIngredientsVolume(coffeeOrderDTO, coffeeType);

        CoffeeOrder coffeeOrder = new CoffeeOrder();
        coffeeOrder.setCoffeeType(coffeeType);
        coffeeOrder.setSugarGrams(coffeeOrderDTO.getSugar());
        return coffeeOrderRepository.save(coffeeOrder);
    }

    private CoffeeType getCoffeeType(CoffeeOrderDTO coffeeOrderDTO) {
        return coffeeTypeRepository.findCoffeeTypeByName(coffeeOrderDTO.getCoffeeType())
                .orElseThrow(() -> new CoffeeTypeNotFoundException("Coffee " + coffeeOrderDTO.getCoffeeType()
                        + " not found"));
    }
}
