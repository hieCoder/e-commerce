package com.hieushsoft.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private int quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalPrice;

    @ElementCollection
    private List<String> ingredients;
}
