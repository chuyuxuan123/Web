package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    private String email;

    private boolean admin;

    private boolean enable;


//    public User(User user_t){
//        this.userId = user_t.userId;
//        this.username = user_t.username;
//        this.password = user_t.password;
//        this.email = user_t.email;
//        this.admin = user_t.admin;
//        this.enable = user_t.enable;
//    }
}
