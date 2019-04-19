package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cartItems")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/{username}/all")
    public @ResponseBody
    List<CartItem> getByUser_Username(@PathVariable("username") String user) {
        for (CartItem c : cartItemRepository.findByUser_Username(user)
        ) {
            JSONObject jsonObject = new JSONObject("{'orderId':" + c.getCartItemId() + "," + "}");

        }
        return cartItemRepository.findByUser_Username(user);
    }
}
