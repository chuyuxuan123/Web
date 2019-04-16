package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private UserOrder userOrder;

    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;

    private int amount;

    private int price;

    @JsonBackReference
    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }
}
