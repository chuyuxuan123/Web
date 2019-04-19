package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByUser_Username(String user);
}
