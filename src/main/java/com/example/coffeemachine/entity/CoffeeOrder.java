package com.example.coffeemachine.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "coffee_order")
public class CoffeeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "int default 0")
    @Min(value = 0)
    private int sugarGrams;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private CoffeeType coffeeType;

    @Column(updatable = false)
    private LocalDateTime orderDate;

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }


}
