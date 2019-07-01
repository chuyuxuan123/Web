package com.example.demo.daoImpl;

import com.example.demo.dao.OrderDao;
import com.example.demo.model.UserOrder;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<UserOrder> findByUser_Username(String username) {
        return orderRepository.findByUser_Username(username);
    }

    @Override
    public List<UserOrder> findByCreateTimeBetweenAndUser_Username(Date startDate, Date endDate, String username) {
        return orderRepository.findByCreateTimeBetweenAndUser_Username(startDate, endDate, username);
    }

    @Override
    public Iterable<UserOrder> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(UserOrder userOrder) {
        orderRepository.save(userOrder);
    }

    @Override
    public List<Object> getBookSales() {
        return orderRepository.getBookSales();
    }

    @Override
    public List<Object> getUserPay() {
        return orderRepository.getUserPay();
    }

    @Override
    public List<Object> getBookSalesBetween(Date start, Date end) {
        return orderRepository.getBookSaleBetweenDate(start, end);
    }

    @Override
    public List<Object> getUserPayBetween(Date start, Date end) {
        return orderRepository.getUserPayBetweenDate(start, end);
    }
}
