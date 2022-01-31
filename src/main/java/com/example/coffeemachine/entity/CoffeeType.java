package com.example.coffeemachine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "coffee_type")
public class CoffeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Min(value = 0)
    private int coffeeGrams;

    @Column(nullable = false)
    @Min(value = 0)
    private int waterMilliliters;

    @Column(nullable = false, columnDefinition = "int default 0")
    @Min(value = 0)
    private int milkMilliliters;

    @Column(nullable = false, columnDefinition = "int default 0")
    @Min(value=0)
    private int milkFoamMilliliters;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "coffeeType")
    @JsonIgnore
    private Set<CoffeeOrder> orders = new HashSet<>();
}
