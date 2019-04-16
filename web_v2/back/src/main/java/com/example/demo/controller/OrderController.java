package com.example.demo.controller;


import com.example.demo.model.OrderItem;
import com.example.demo.model.UserOrder;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
