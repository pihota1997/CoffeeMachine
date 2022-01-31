package com.example.coffeemachine.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredient_volume")
public class AvailableIngredientVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    @Min(value = 0)
    private int availableVolume;

}
