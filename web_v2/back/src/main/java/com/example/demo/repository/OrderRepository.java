package com.example.demo.repository;

import com.example.demo.model.UserOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public interface OrderRepository extends CrudRepository<UserOrder, Long> {
    List<UserOrder> findByUser_Username(String username);

    List<UserOrder> findByCreateTimeBetweenAndUser_Username(Date startDate, Date endDate, String username);

    @Query(value = "SELECT " +
            "book_id," +
            "bookname," +
            "SUM( amount ) " +
            "FROM " +
            " ( " +
            "SELECT " +
            "book_id, " +
            "bookname, " +
            "amount " +
            "FROM " +
            "( SELECT book_id, amount FROM user_order NATURAL JOIN order_item WHERE deleted = 0 ) AS a " +
            "NATURAL JOIN book " +
            " ) AS b " +
            "GROUP BY " +
            "book_id," +
            "bookname; ", nativeQuery = true)
    List<Object> getBookSale();

    @Query(value = "SELECT " +
            "user_id, " +
            "username, " +
            "sum( total_price ) " +
            "FROM " +
            "( SELECT user_id, total_price, username FROM ( SELECT order_id, user_id, total_price FROM user_order ) AS a NATURAL JOIN USER ) AS b " +
            "GROUP BY " +
            "user_id, " +
            "username;", nativeQuery = true)
    List<Object> getUserPay();
}