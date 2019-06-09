package com.example.demo.daoImpl;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public void updateEnableByUsername(String username, boolean enable) {
        userRepository.updateEnableByUsername(username, enable);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user, UserInfo userInfo) {
        userRepository.save(user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void setAvatar(String username, Binary avatar) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        userInfo.setAvatar(avatar);
        userInfoRepository.save(userInfo);
    }

    @Override
    public byte[] getUserAvatar(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        return userInfo.getAvatar().getData();
    }
}
