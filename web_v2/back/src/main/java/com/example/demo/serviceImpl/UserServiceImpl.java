package com.example.demo.serviceImpl;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public void updateEnableByUsername(String username, boolean enable) {
        userDao.updateEnableByUsername(username, enable);
    }

    @Override
    public String addNewUser(String username, String email, String password) {
        if (userDao.getByUsername(username) != null) {
            return "duplicate";
        }

        User user = new User();
        user.setAdmin(false);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnable(true);
        userDao.save(user);
        return "saved";
    }
}
