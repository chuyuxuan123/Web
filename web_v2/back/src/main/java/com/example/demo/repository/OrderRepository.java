package com.example.demo.repository;

import com.example.demo.model.UserOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<UserOrder, Long> {
    List<UserOrder> findByUser_Username(String username);
}
