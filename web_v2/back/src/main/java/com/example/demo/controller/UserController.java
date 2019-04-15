package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/auth")
    @ResponseBody
    public String updateEnableByUsername(@RequestParam("user") String username, @RequestParam("enable") String enable) {
        if (enable.equals("false")) {
            userRepository.updateEnableByUsername(username, false);
        } else {
            userRepository.updateEnableByUsername(username, true);
        }
        return "updated";
    }
}
