package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    Iterable<User> getAllUsers();

    User getByUsername(String username);

    void updateEnableByUsername(String username, boolean enable);

    String addNewUser(String username, String email, String password);
}
