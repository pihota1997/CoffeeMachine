package com.example.coffeemachine.web;

import com.example.coffeemachine.DTO.CoffeeOrderDTO;
import com.example.coffeemachine.entity.CoffeeOrder;
import com.example.coffeemachine.service.CoffeeOrderService;
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

@RestController
@RequestMapping("/")
public class CoffeeOrderController {

    private final ResponseErrorValidation responseErrorValidation;
    private final CoffeeOrderService coffeeOrderService;

    @Autowired
    public CoffeeOrderController(ResponseErrorValidation responseErrorValidation,
                                 CoffeeOrderService coffeeOrderService) {
        this.responseErrorValidation = responseErrorValidation;
        this.coffeeOrderService = coffeeOrderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Object> makeOrder(@Valid @RequestBody CoffeeOrderDTO coffeeOrderDTO,
                                            BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))
            return errors;

        CoffeeOrder coffeeOrder = coffeeOrderService.createOrder(coffeeOrderDTO);
        return new ResponseEntity<>(coffeeOrder, HttpStatus.OK);
    }
}
