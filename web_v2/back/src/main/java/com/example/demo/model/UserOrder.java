package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserOrder {

    @Id
    @GeneratedValue
    private Long orderId;

    @CreatedDate
    @Column(name = "order_time")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "userOrder", cascade = {CascadeType.ALL})
    private List<OrderItem> orderItems;

    private Integer totalPrice;

    private boolean deleted;
}
