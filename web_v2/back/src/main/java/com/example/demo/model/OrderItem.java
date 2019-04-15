package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long orderId;

    private String bookISBN;

    private int amount;

    private int price;


}
