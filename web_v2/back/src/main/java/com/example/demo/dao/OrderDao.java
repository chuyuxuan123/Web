package com.example.demo.dao;

import com.example.demo.model.UserOrder;

import java.util.Date;
import java.util.List;

public interface OrderDao {

    List<UserOrder> findByUser_Username(String username);

    List<UserOrder> findByCreateTimeBetweenAndUser_Username(Date startDate, Date endDate, String username);

    Iterable<UserOrder> findAll();

    void save(UserOrder userOrder);
}
