package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"isbn", "bookname"}))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String isbn;

    private String bookname;

    private String author;

    private Integer inventory;

    private String cover;

    private Integer price;

    private boolean deleted;
}
