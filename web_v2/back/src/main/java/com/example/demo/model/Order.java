package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.AuditingBeanDefinitionParser;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue
    private Long orderId;

    //TODO: has error when create table using type date , try string
    @CreatedDate
    @Column(name = "order_time")
    private Date createTime;


    private String username;
}
