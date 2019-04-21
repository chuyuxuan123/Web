package com.example.demo.controller;


import com.example.demo.model.Book;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/all")
    public @ResponseBody
    Iterable<UserOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/allItems")
    public @ResponseBody
    Iterable<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @GetMapping("/{username}/all")
    public @ResponseBody
    List<UserOrder> getUserAllOrders(@PathVariable("username") String username,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     HttpSession session) {
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


        User user = (User) session.getAttribute("user");
        //TODO:返回前端过多数据，应当在后端筛选出需要的数据
        return orderRepository.findByUser_Username(user.getUsername());
    }

//    @GetMapping("/{username}/jsonall")
//    public @ResponseBody
//    List<JSONObject> getJson(@PathVariable("username") String username){
//    }

    @PostMapping(value = "/{username}/buy")
    public @ResponseBody
    Integer addOrder(@RequestBody String data, @PathVariable("username") String username) {
        System.out.println(data);
        JSONObject jsonObject = new JSONObject(data);
        Book book = bookRepository.findByBookname(jsonObject.get("bookname").toString());

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(userRepository.getByUsername(username));

        OrderItem orderItem = new OrderItem();
        orderItem.setUserOrder(userOrder);
        orderItem.setBook(book);
        orderItem.setAmount(jsonObject.getInt("amount"));
        orderItem.setPrice(jsonObject.getInt("price"));

        Date date = new Date();
        userOrder.setCreateTime(date);
        orderRepository.save(userOrder);
        orderItemRepository.save(orderItem);
        book.setInventory(book.getInventory() - jsonObject.getInt("amount"));
        bookRepository.save(book);
        return 200;
    }

}
