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
}
