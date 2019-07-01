package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;

import java.util.Date;
import java.util.List;

public interface OrderService {

    List<JSONObject> getUserAllOrders(User user);

    List<JSONObject> getOrdersByDateAndUsername(Date begin, Date end, String username);

    Integer createOrder(JSONArray items, User user);

    List<JSONObject> getBookSales();

    List<JSONObject> getBookSalesBetween(Date start, Date end);

    List<JSONObject> getUserPay();

    List<JSONObject> getUserPayBetween(Date start, Date end);
}
