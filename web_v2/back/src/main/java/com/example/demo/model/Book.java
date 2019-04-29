package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

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

    @Min(value = 0, message = "库存不足")
    private Integer inventory;

    private String cover;

    private Integer price;

    private boolean deleted;
}
