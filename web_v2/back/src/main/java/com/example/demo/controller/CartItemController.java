package com.example.demo.controller;

import com.example.demo.dto.CartItemRequest;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cartItems")
public class CartItemController {

    @Autowired
    WebApplicationContext applicationContext;

    @GetMapping("/all")
    public @ResponseBody
    List<CartItem> getAllCartItems() {
//        System.out.println(session.getId());
//        User user = (User) session.getAttribute("user");
//        if (session.getAttribute("cart") == null) {
//            Cart cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
        CartService cartService = applicationContext.getBean(CartService.class);
//        return ((Cart) (session.getAttribute("cart"))).getCartItemList();
        return cartService.getAllCartItems();
    }

    //TODO:考虑把一件商品添加到购物车中时，商品的价格会不会改变
    @PostMapping("/add")
    public @ResponseBody
    Integer addCartItem(@RequestBody CartItemRequest data) {
//        System.out.println(session.getId());
//        User user = (User) session.getAttribute("user");
//        JSONObject jsonObject = new JSONObject(data);
//        if (session.getAttribute("cart") == null) {
//            Cart cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//
//        Cart cart = (Cart) session.getAttribute("cart");
//        CartItem cartItem = new CartItem();
//        cartItem.setBookId(jsonObject.getInteger("bookId"));
//        cartItem.setBookname(jsonObject.getString("bookname"));
//        cartItem.setAmount(jsonObject.getInteger("amount"));
//        cartItem.setPrice(jsonObject.getInteger("price"));
//        cart.add(cartItem);
        CartService cartService = applicationContext.getBean(CartService.class);
        cartService.addNewItem(data);
        return 200;
    }

    @GetMapping("/remove")
    public @ResponseBody
    String removeCartItem(@RequestParam("bookId") Integer bookId) {
//        System.out.println(bookname);
//        User user = (User) session.getAttribute("user");
//        Cart cart = (Cart) session.getAttribute("cart");
//        cart.removeByBookId(bookId);
        CartService cartService = applicationContext.getBean(CartService.class);
        cartService.removeItem(bookId);
        return "200";

    }


}
