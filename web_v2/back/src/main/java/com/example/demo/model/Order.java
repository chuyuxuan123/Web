package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue
    private int id;

    private Date date;

    private String username;
}
