package com.example.demo.service;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> getAllCartItems();

    void addNewItem(CartItemRequest cartItemRequest);

    void removeItem(Integer bookId);
}
