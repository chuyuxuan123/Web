package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.*;

import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public @ResponseBody
    List<JSONObject> getUserAllOrders(HttpSession session) {

        User user = (User) session.getAttribute("user");
        return orderService.getUserAllOrders(user);
    }

    @GetMapping("/search")
    public @ResponseBody
    List<JSONObject> getOrdersByDateAndUsername(@RequestParam("start") String begin,
                                                @RequestParam("end") String end,
                                                @RequestParam("username") String username,
                                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = format.parse(begin + " 00:00:00");
            Date endDate = format.parse(end + " 00:00:00");
            if (user.isAdmin()) {
                return orderService.getOrdersByDateAndUsername(startDate, endDate, username);
            }
            return orderService.getOrdersByDateAndUsername(startDate, endDate, user.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //books sale
    @GetMapping("/sales")
    public @ResponseBody
    List<JSONObject> getBookSales() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Object o : orderRepository.getBookSale()
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject object = new JSONObject();
            object.put("bookId", ((BigInteger) rowArray[0]).longValue());
            object.put("bookName", (String) rowArray[1]);
            object.put("sales", ((BigDecimal) rowArray[2]).intValue());
            jsonObjects.add(object);
        }
        return jsonObjects;
    }

    @GetMapping("/pay")
    public List<JSONObject> getUserPay() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Object o : orderRepository.getUserPay()
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", rowArray[0]);
            jsonObject.put("username", rowArray[1]);
            jsonObject.put("pay", rowArray[2]);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }



    // post method begin


    @PostMapping(value = "/buy")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    Integer addOrder(@RequestBody JSONArray data,
                     HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (orderService.createOrder(data, user) == 200)
            return 200;

        return 400;
    }

    //TODO:只能一次处理所有的订单，并且出错之后无法撤销
    @PostMapping("/cart/buy")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    Integer buySome(HttpSession session, @RequestBody JSONArray items) {

        JSONArray jsonItems = new JSONArray(items);

        User user = (User) session.getAttribute("user");

        for (Object item:jsonItems
             ) {
            ((Cart) session.getAttribute("cart")).removeByBookId((Integer) ((LinkedHashMap) item).get("bookId"));
        }

        return orderService.createOrder(items, user);
    }


}
