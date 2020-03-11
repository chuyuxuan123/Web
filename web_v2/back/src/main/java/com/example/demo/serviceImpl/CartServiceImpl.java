package com.example.demo.serviceImpl;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("session")
public class CartServiceImpl implements CartService {

    private final Cart cart;

    public CartServiceImpl() {
        this.cart = new Cart();
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cart.getCartItemList();
    }

    @Override
    public void addNewItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem();
        cartItem.setBookId(cartItemRequest.getBookId());
        cartItem.setBookname(cartItemRequest.getBookname());
        cartItem.setAmount(cartItemRequest.getAmount());
        cartItem.setPrice(cartItemRequest.getPrice());
        cart.add(cartItem);
    }

    @Override
    public void removeItem(Integer bookId) {
        cart.removeByBookId(bookId);
    }
}
