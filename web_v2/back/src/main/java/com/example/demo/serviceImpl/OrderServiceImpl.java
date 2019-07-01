package com.example.demo.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.BookDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.OrderItemDao;
import com.example.demo.model.Book;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.service.OrderService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<JSONObject> getUserAllOrders(User user) {
        List<UserOrder> userOrders = new ArrayList<>();

        if (user.isAdmin()) {
            userOrders = Lists.newArrayList(orderDao.findAll());
        } else {
            userOrders = orderDao.findByUser_Username(user.getUsername());
        }
        return backToFront(userOrders);
    }

    @Override
    public List<JSONObject> getOrdersByDateAndUsername(Date begin, Date end, String username) {
        List<UserOrder> userOrders = orderDao.findByCreateTimeBetweenAndUser_Username(begin, end, username);

        return backToFront(userOrders);
    }

    @Override
    public Integer createOrder(JSONArray items, User user) {
        System.out.println(items);
        JSONArray jsonItems = new JSONArray(items);
        System.out.println(jsonItems);

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        userOrder.setOrderItems(orderItems);

        Integer totalPrice = 0;
        for (Object item : jsonItems
        ) {
            System.out.println(item);
            OrderItem orderItem = new OrderItem();
            Long bookId = ((Integer) ((LinkedHashMap) item).get("bookId")).longValue();
            Integer amount = (Integer) ((LinkedHashMap) item).get("amount");
            Book book = bookDao.findByBookId(bookId);
            orderItem.setUserOrder(userOrder);
            orderItem.setPrice(book.getPrice());
            orderItem.setAmount(amount);
            orderItem.setBook(book);

            book.setInventory(book.getInventory() - amount);
            bookDao.save(book);

            orderItems.add(orderItem);

            totalPrice = totalPrice + amount * book.getPrice();

        }
        userOrder.setTotalPrice(totalPrice);
        userOrder.setOrderItems(orderItems);


        orderDao.save(userOrder);


        return 200;
    }

    @Override
    public List<JSONObject> getBookSales() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        int index = 0;
        for (Object o : orderDao.getBookSales()
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject object = new JSONObject();
            object.put("key", index);
            object.put("bookId", ((BigInteger) rowArray[0]).longValue());
            object.put("bookName", (String) rowArray[1]);
            object.put("sales", ((BigDecimal) rowArray[2]).intValue());
            jsonObjects.add(object);
            index++;
        }
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("sales") - o1.getInteger("sales");
            }
        });
        return jsonObjects;

    }

    @Override
    public List<JSONObject> getBookSalesBetween(Date start, Date end) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        int index = 0;
        for (Object o : orderDao.getBookSalesBetween(start, end)
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject object = new JSONObject();
            object.put("key", index);
            object.put("bookId", ((BigInteger) rowArray[0]).longValue());
            object.put("bookName", (String) rowArray[1]);
            object.put("sales", ((BigDecimal) rowArray[2]).intValue());
            jsonObjects.add(object);
            index++;
        }
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("sales") - o1.getInteger("sales");
            }
        });
        return jsonObjects;
    }

    @Override
    public List<JSONObject> getUserPay() {
        List<JSONObject> jsonObjects = new ArrayList<>();
        int index = 0;
        for (Object o : orderDao.getUserPay()
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", index);
            jsonObject.put("user_id", rowArray[0]);
            jsonObject.put("username", rowArray[1]);
            jsonObject.put("pay", rowArray[2]);
            jsonObjects.add(jsonObject);
            index++;
        }
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("pay") - o1.getInteger("pay");
            }
        });
        return jsonObjects;
    }

    @Override
    public List<JSONObject> getUserPayBetween(Date start, Date end) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        int index = 0;
        for (Object o : orderDao.getUserPayBetween(start, end)
        ) {
            Object[] rowArray = (Object[]) o;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", index);
            jsonObject.put("user_id", rowArray[0]);
            jsonObject.put("username", rowArray[1]);
            jsonObject.put("pay", rowArray[2]);
            jsonObjects.add(jsonObject);
            index++;
        }
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("pay") - o1.getInteger("pay");
            }
        });
        return jsonObjects;
    }

    private List<JSONObject> backToFront(List<UserOrder> userOrders) {
        ArrayList<JSONObject> returnToFront = new ArrayList<JSONObject>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < userOrders.size(); i++) {
            UserOrder userOrder = userOrders.get(i);
            List<OrderItem> orderItems = userOrder.getOrderItems();

            JSONObject jsonOrder = new JSONObject();
            jsonOrder.put("key", userOrder.getOrderId());
            jsonOrder.put("orderId", userOrder.getOrderId());
            jsonOrder.put("createTime", format.format(userOrder.getCreateTime()));
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
