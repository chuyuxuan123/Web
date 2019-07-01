package com.example.demo.service;

import com.example.demo.model.User;
import org.bson.types.Binary;

import java.util.ArrayList;

public interface UserService {

    Iterable<User> getAllUsers();

    User getByUsername(String username);

    void updateEnableByUsername(String username, boolean enable);

    String addNewUser(String username, String email, String password);

    void setUserAvatar(String username, Binary avatar);

    byte[] getUserAvatar(String username);

    ArrayList<User> getAllUsersExceptAdmin();
}
