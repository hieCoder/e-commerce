package com.hieushsoft.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

//    private Payment payment;

    private int totalItem;

    private float totalPrice;
}
