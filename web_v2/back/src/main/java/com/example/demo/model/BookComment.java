package com.example.demo.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class BookComment {

    @Id
    private String id;

    private Integer bookId;

    private String username;

    private String content;

    private Date createDate;


}
