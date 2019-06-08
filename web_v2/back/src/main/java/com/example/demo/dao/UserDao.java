package com.example.demo.dao;

import com.example.demo.model.User;

public interface UserDao {

    void updateEnableByUsername(String username, boolean enable);

    User getByUsername(String username);

    Iterable<User> findAll();

    void save(User user);
}
