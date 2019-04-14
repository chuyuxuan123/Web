package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Book {

    @Id
    private String ISBN;

    private String bookname;

    private String author;

    private Integer inventory;

    private String cover;

    private Integer price;
}
