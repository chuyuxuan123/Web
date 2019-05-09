package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {

    List<JSONObject> getUserAllOrders(User user);
}
