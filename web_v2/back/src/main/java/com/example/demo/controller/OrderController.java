package com.example.demo.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // get order information begin

    @GetMapping("/allItems")
    public @ResponseBody
    Iterable<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public @ResponseBody
    List<JSONObject> getUserAllOrders(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) {
            Iterable<UserOrder> userOrders = orderRepository.findAll();
            ArrayList<JSONObject> returnToFront = new ArrayList<JSONObject>();


            Iterator<UserOrder> iterator = userOrders.iterator();
            while (iterator.hasNext()) {
                UserOrder userOrder = iterator.next();
                List<OrderItem> orderItems = userOrder.getOrderItems();

                JSONObject jsonOrder = new JSONObject();
                jsonOrder.put("key", userOrder.getOrderId());
                jsonOrder.put("orderId", userOrder.getOrderId());
                //TODO:使用fastjson导致时间格式不对
                jsonOrder.put("createTime", userOrder.getCreateTime());
                jsonOrder.put("username", userOrder.getUser().getUsername());
                jsonOrder.put("totalPrice", userOrder.getTotalPrice());

                ArrayList<JSONObject> jsonOrderItems = new ArrayList<>();

                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("price", orderItem.getPrice());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    jsonOrderItems.add(jsonObject);
                }
                jsonOrder.put("detail", jsonOrderItems);
                returnToFront.add(jsonOrder);

            }
            Collections.reverse(returnToFront);
            return returnToFront;

        } else {

            List<UserOrder> userOrders = orderRepository.findByUser_Username(user.getUsername());
            ArrayList<JSONObject> returnToFront = new ArrayList<>();

            for (int i = 0; i < userOrders.size(); i++) {
                UserOrder userOrder = userOrders.get(i);
                List<OrderItem> orderItems = userOrder.getOrderItems();

                JSONObject jsonOrder = new JSONObject();
                jsonOrder.put("key", userOrder.getOrderId());
                jsonOrder.put("orderId", userOrder.getOrderId());
                jsonOrder.put("createTime", userOrder.getCreateTime());
                jsonOrder.put("username", userOrder.getUser().getUsername());
                jsonOrder.put("totalPrice", userOrder.getTotalPrice());

                ArrayList<JSONObject> jsonOrderItems = new ArrayList<>();

                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("price", orderItem.getPrice());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    jsonOrderItems.add(jsonObject);
                }
                jsonOrder.put("detail", jsonOrderItems);
                returnToFront.add(jsonOrder);

            }
            Collections.reverse(returnToFront);
            return returnToFront;
        }
    }

//    @GetMapping(value= "/testget",produces = "application/json;charset=UTF-8")
//    public @ResponseBody
//    String test() {
//
//        List<UserOrder> userOrders = orderRepository.findByUser_Username("t3");
//        ArrayList<JSONObject> returnToFront = new ArrayList<>();
//
//        for (int i = 0; i < userOrders.size(); i++) {
//            UserOrder userOrder = userOrders.get(i);
//            List<OrderItem> orderItems = userOrder.getOrderItems();
//
//            JSONObject jsonOrder = new JSONObject();
//            jsonOrder.put("key", userOrder.getOrderId());
//            jsonOrder.put("orderId", userOrder.getOrderId());
//            jsonOrder.put("createTime", userOrder.getCreateTime());
//            jsonOrder.put("username", userOrder.getUser().getUsername());
//            jsonOrder.put("totalPrice",userOrder.getTotalPrice());
//
//            ArrayList<JSONObject> jsonOrderItems = new ArrayList<>();
//
//            for (int j = 0; j < orderItems.size(); j++) {
//                OrderItem orderItem = orderItems.get(j);
//
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("bookname", orderItem.getBook().getBookname());
//                jsonObject.put("amount", orderItem.getAmount());
//                jsonObject.put("price",orderItem.getPrice());
//                jsonObject.put("isbn", orderItem.getBook().getIsbn());
//
//                jsonOrderItems.add(jsonObject);
//            }
//            jsonOrder.put("detail",jsonOrderItems);
//            returnToFront.add(jsonOrder);
//
//        }
//        return returnToFront.toString();
//    }

    @GetMapping("/search")
    public @ResponseBody
    List<JSONObject> getOrdersByDateAndUsername(@RequestParam("start") String begin,
                                                @RequestParam("end") String end,
                                                @RequestParam("bookname") String bookname,
                                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = format.parse(begin + " 00:00:00");
            Date endDate = format.parse(end + " 00:00:00");

            List<UserOrder> userOrders = orderRepository.findByCreateTimeBetweenAndUser_Username(startDate, endDate, user.getUsername());
            ArrayList<JSONObject> returnToFront = new ArrayList<>();

            for (int i = 0; i < userOrders.size(); i++) {
                UserOrder userOrder = userOrders.get(i);
                List<OrderItem> orderItems = userOrder.getOrderItems();

                JSONObject jsonOrder = new JSONObject();
                jsonOrder.put("key", userOrder.getOrderId());
                jsonOrder.put("orderId", userOrder.getOrderId());
                jsonOrder.put("createTime", userOrder.getCreateTime());
                jsonOrder.put("username", userOrder.getUser().getUsername());
                jsonOrder.put("totalPrice", userOrder.getTotalPrice());

                ArrayList<JSONObject> jsonOrderItems = new ArrayList<>();

                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("price", orderItem.getPrice());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    jsonOrderItems.add(jsonObject);
                }
                jsonOrder.put("detail", jsonOrderItems);
                returnToFront.add(jsonOrder);

            }
            return returnToFront;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // get order information end

    // post method begin


    @PostMapping(value = "/buy")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    Integer addOrder(@RequestBody JSONObject data,
                     HttpSession session) {
//        System.out.println(data);
        User user = (User) session.getAttribute("user");

        JSONObject jsonObject = new JSONObject(data);
        Book book = bookRepository.findByBookname(jsonObject.get("bookname").toString());

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(userRepository.getByUsername(user.getUsername()));

        OrderItem orderItem = new OrderItem();
        orderItem.setUserOrder(userOrder);
        orderItem.setBook(book);
        orderItem.setAmount(jsonObject.getInteger("amount"));
        orderItem.setPrice(jsonObject.getInteger("price"));

        Integer totalPrice = jsonObject.getInteger("amount") * jsonObject.getInteger("price");
        userOrder.setTotalPrice(totalPrice);

        Date date = new Date();
        userOrder.setCreateTime(date);
        orderRepository.save(userOrder);
        orderItemRepository.save(orderItem);
        book.setInventory(book.getInventory() - jsonObject.getInteger("amount"));
        bookRepository.save(book);
        return 200;
    }

    //TODO:只能一次处理所有的订单，并且出错之后无法撤销
    @PostMapping("/cart/buy")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    Integer buySome(HttpSession session, @RequestBody JSONArray items) {
        System.out.println(items);
        JSONArray jsonItems = new JSONArray(items);
        System.out.println(jsonItems);

        User user = (User) session.getAttribute("user");
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        userOrder.setOrderItems(orderItems);

        Integer totalPrice = 0;
        for (Object item:jsonItems
             ) {
            OrderItem orderItem = new OrderItem();
            Book book = bookRepository.findByBookname(((JSONObject)item).getString("bookname"));

            orderItem.setUserOrder(userOrder);
            orderItem.setPrice(book.getPrice());
            orderItem.setAmount(((JSONObject) item).getInteger("amount"));
            orderItem.setBook(book);

            book.setInventory(book.getInventory() - ((JSONObject) item).getInteger("amount"));
            bookRepository.save(book);

            orderItems.add(orderItem);

            totalPrice = totalPrice + ((JSONObject) item).getInteger("amount") * book.getPrice();

            ((Cart)session.getAttribute("cart")).removeByBookname(((JSONObject)item).getString("bookname"));
        }
        userOrder.setTotalPrice(totalPrice);
        userOrder.setOrderItems(orderItems);
        for (OrderItem orderItem : orderItems
        ) {
            orderItemRepository.save(orderItem);
        }

        orderRepository.save(userOrder);


        return 200;
    }


}
