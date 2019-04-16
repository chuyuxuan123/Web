package com.example.demo.repository;

import com.example.demo.model.UserOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<UserOrder, Long> {
}
