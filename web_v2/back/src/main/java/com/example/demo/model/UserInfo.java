package com.example.demo.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
public class UserInfo {

    @Id
    private String id;

    private String username;

    private Binary avatar;


}
