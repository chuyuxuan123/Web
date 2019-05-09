package com.example.demo.serviceImpl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void updateEnableByUsername(String username, boolean enable) {
        userRepository.updateEnableByUsername(username, enable);
    }

    @Override
    public String addNewUser(String username, String email, String password) {
        if (userRepository.getByUsername(username) != null) {
            return "duplicate";
        }

        User user = new User();
        user.setAdmin(false);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnable(true);
        userRepository.save(user);
        return "saved";
    }
}
