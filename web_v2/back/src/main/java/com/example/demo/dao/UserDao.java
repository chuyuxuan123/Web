package com.example.demo.dao;

import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import org.bson.types.Binary;

public interface UserDao {

    void updateEnableByUsername(String username, boolean enable);

    User getByUsername(String username);

    Iterable<User> findAll();

    void save(User user, UserInfo userInfo);

    void setAvatar(String username, Binary avatar);

    byte[] getUserAvatar(String username);
}
