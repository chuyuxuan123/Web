package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import javafx.print.Collation;
import org.json.JSONArray;
import org.json.JSONObject;
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
    String getUserAllOrders(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) {
            Iterable<UserOrder> userOrders = orderRepository.findAll();
            ArrayList<JSONObject> returnToFront = new ArrayList<JSONObject>();


            Iterator<UserOrder> iterator = userOrders.iterator();
            while (iterator.hasNext()) {
                UserOrder userOrder = iterator.next();
                List<OrderItem> orderItems = userOrder.getOrderItems();
                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("orderId", userOrder.getOrderId());
                    jsonObject.put("createTime", userOrder.getCreateTime());
                    jsonObject.put("username", userOrder.getUser().getUsername());
                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    returnToFront.add(jsonObject);
                }
            }
            Collections.reverse(returnToFront);
            return returnToFront.toString();

        } else {

            List<UserOrder> userOrders = orderRepository.findByUser_Username(user.getUsername());
            ArrayList<JSONObject> returnToFront = new ArrayList<>();
            for (int i = 0; i < userOrders.size(); i++) {
                UserOrder userOrder = userOrders.get(i);
                List<OrderItem> orderItems = userOrder.getOrderItems();
                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("orderId", userOrder.getOrderId());
                    jsonObject.put("createTime", userOrder.getCreateTime());
                    jsonObject.put("username", userOrder.getUser().getUsername());
                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    returnToFront.add(jsonObject);
                }
            }
            Collections.reverse(returnToFront);
            return returnToFront.toString();
        }
    }

    @GetMapping("/search")
    public @ResponseBody
    String getOrdersByDateAndUsername(@RequestParam("start") String begin,
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
                for (int j = 0; j < orderItems.size(); j++) {
                    OrderItem orderItem = orderItems.get(j);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("orderId", userOrder.getOrderId());
                    jsonObject.put("createTime", userOrder.getCreateTime());
                    jsonObject.put("username", userOrder.getUser().getUsername());
                    jsonObject.put("bookname", orderItem.getBook().getBookname());
                    jsonObject.put("amount", orderItem.getAmount());
                    jsonObject.put("isbn", orderItem.getBook().getIsbn());

                    returnToFront.add(jsonObject);
                }
            }

            return returnToFront.toString();

//            return orderRepository.findByCreateTimeBetweenAndUser_Username(startDate,endDate,user.getUsername());
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
    Integer addOrder(@RequestBody String data,
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
        orderItem.setAmount(jsonObject.getInt("amount"));
        orderItem.setPrice(jsonObject.getInt("price"));

        Integer totalPrice = jsonObject.getInt("amount") * jsonObject.getInt("price");
        userOrder.setTotalPrice(totalPrice);

        Date date = new Date();
        userOrder.setCreateTime(date);
        orderRepository.save(userOrder);
        orderItemRepository.save(orderItem);
        book.setInventory(book.getInventory() - jsonObject.getInt("amount"));
        bookRepository.save(book);
        return 200;
    }

    //TODO:只能一次处理所有的订单，并且出错之后无法撤销
    @PostMapping("/cart/buy")
    @Transactional(rollbackFor = Exception.class)
    public @ResponseBody
    Integer buySome(HttpSession session,@RequestBody String items){
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
            orderItem.setAmount(((JSONObject)item).getInt("amount"));
            orderItem.setBook(book);

            book.setInventory(book.getInventory() - ((JSONObject)item).getInt("amount"));
            bookRepository.save(book);

            orderItems.add(orderItem);

            totalPrice = totalPrice + ((JSONObject)item).getInt("amount")*book.getPrice();

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


    //下面的代码主要的问题是只能把全部购物车中的书全部购买，修改后的代码在上面的函数中
//    @GetMapping("/cart/buy")
//    public @ResponseBody
//    Integer buyAllItems(HttpSession session) {
////        System.out.println("catch signal");
//        User user = (User) session.getAttribute("user");
//        Cart cart = (Cart) session.getAttribute("cart");
//
//        UserOrder userOrder = new UserOrder();
//        userOrder.setUser(user);
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        userOrder.setOrderItems(orderItems);
//
//        Integer totalPrice = 0;
//        for (CartItem cartItem : cart.getCartItemList()
//        ) {
//            OrderItem orderItem = new OrderItem();
//            Book book = bookRepository.findByBookname(cartItem.getBookname());
//
//            orderItem.setUserOrder(userOrder);
//            orderItem.setPrice(cartItem.getPrice());
//            orderItem.setAmount(cartItem.getAmount());
//            orderItem.setBook(book);
//
//            book.setInventory(book.getInventory() - cartItem.getAmount());
//            bookRepository.save(book);
//
//            orderItems.add(orderItem);
//
////            orderItemRepository.save(orderItem);
//            totalPrice = totalPrice + cartItem.getPrice() * cartItem.getAmount();
//        }
//
//        userOrder.setTotalPrice(totalPrice);
//        userOrder.setOrderItems(orderItems);
//        for (OrderItem orderItem : orderItems
//        ) {
//            orderItemRepository.save(orderItem);
//        }
//
//        orderRepository.save(userOrder);
//
//        cart.removeAll();
//
//        return 200;
//    }

}
