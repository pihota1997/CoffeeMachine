package com.example.coffeemachine.web;

import com.example.coffeemachine.DTO.AvailableIngredientVolumeDTO;
import com.example.coffeemachine.entity.AvailableIngredientVolume;
import com.example.coffeemachine.service.AvailableIngredientVolumeService;
import com.example.coffeemachine.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class AvailableIngredientVolumeController {

    private final ResponseErrorValidation responseErrorValidation;
    private final AvailableIngredientVolumeService addIngredientsService;

    @Autowired
    public AvailableIngredientVolumeController(ResponseErrorValidation responseErrorValidation, AvailableIngredientVolumeService addIngredientsService) {
        this.responseErrorValidation = responseErrorValidation;
        this.addIngredientsService = addIngredientsService;
    }

    @PostMapping("/addIngredients")
    public ResponseEntity<Object> addIngredients(@Valid @RequestBody AvailableIngredientVolumeDTO[] availableIngredientVolumeDTO,
                                                 BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))
            return errors;

        List<AvailableIngredientVolume> availableIngredientVolumeList =
                addIngredientsService.increaseIngredientVolume(availableIngredientVolumeDTO);

        return new ResponseEntity<>(availableIngredientVolumeList, HttpStatus.OK);
    }



}
