package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

//@Entity
//@Data
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cartItemId;
//
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;
//
//    @OneToOne
//    @JoinColumn(name = "bookId")
//    private Book book;
//
//    private int amount;
//}

@Data
public class CartItem{

    private String bookname;

    private Integer amount;

    private Integer price;

}
