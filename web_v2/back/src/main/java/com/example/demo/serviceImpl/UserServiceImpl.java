package com.example.demo.serviceImpl;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public ArrayList<User> getAllUsersExceptAdmin() {
        Iterable<User> allUser = userDao.findAll();
        Iterator<User> it = allUser.iterator();
        ArrayList<User> allUserExceptAdmin = new ArrayList<>();
        allUser.forEach(user -> {
            if (!user.isAdmin()) {
                allUserExceptAdmin.add(user);
            }
        });
        return allUserExceptAdmin;
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
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);

        userDao.save(user, userInfo);

        return "saved";
    }

    @Override
    public void setUserAvatar(String username, Binary avatar) {
        userDao.setAvatar(username, avatar);
    }

    @Override
    public byte[] getUserAvatar(String username) {
        return userDao.getUserAvatar(username);
    }

}
