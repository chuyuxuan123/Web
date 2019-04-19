package com.example.demo.controller;


import com.example.demo.model.OrderItem;
import com.example.demo.model.UserOrder;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/all")
    public @ResponseBody
    Iterable<UserOrder> getAllOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("/allItems")
    public @ResponseBody
    Iterable<OrderItem> getAllOrderItems(){
        return orderItemRepository.findAll();
    }

    @GetMapping("/{username}/all")
    public @ResponseBody
    List<UserOrder> getUserAllOrders(@PathVariable("username")String username){
        return orderRepository.findByUser_Username(username);
    }

//    @GetMapping("/{username}/jsonall")
//    public @ResponseBody
//    List<JSONObject> getJson(@PathVariable("username") String username){
//    }

}
