package com.example.demo.dao;

import com.example.demo.model.OrderItem;

public interface OrderItemDao {

    Iterable<OrderItem> findAll();

    void save(OrderItem orderItem);
}
