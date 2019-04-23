package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItemList;

    public Cart() {
        cartItemList = new ArrayList<CartItem>();
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void add(CartItem cartItem) {
        cartItemList.add(cartItem);
        return;
    }

    public void remove(CartItem cartItem) {
        cartItemList.remove(cartItem);
        return;
    }
}
