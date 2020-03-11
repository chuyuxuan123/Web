package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItemList;

    public Cart() {
        cartItemList = new ArrayList<>();
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void add(CartItem cartItem) {
        for (CartItem c : cartItemList
        ) {
            if (c.getBookId().equals(cartItem.getBookId())) {
                c.setAmount(c.getAmount() + cartItem.getAmount());
                return;
            }
        }
        cartItemList.add(cartItem);
    }

    public void remove(CartItem cartItem) {
        cartItemList.remove(cartItem);
    }

    public void removeAll() {
        cartItemList.clear();
    }

    public void removeByBookname(String bookname) {
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getBookname().contentEquals(bookname)) {
                cartItemList.remove(i);
                return;
            }
        }
    }

    public void removeByBookId(Integer bookId) {
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getBookId().equals(bookId)) {
                cartItemList.remove(i);
                return;
            }
        }
    }
}
