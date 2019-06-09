package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@CrossOrigin
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/auth")
    @ResponseBody
    public String updateEnableByUsername(@RequestParam("user") String username, @RequestParam("enable") String enable) {
        if (enable.equals("false")) {
            userService.updateEnableByUsername(username, false);
        } else {
            userService.updateEnableByUsername(username, true);
        }
        return "updated";
    }

    @GetMapping("/registration")
    @ResponseBody
    public String addNewUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        return userService.addNewUser(username, email, password);
    }

    @GetMapping("/sign")
    @ResponseBody
    public String validateUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session) {


        User user = userService.getByUsername(username);
        if (user.getPassword().equals(password)) {
            if (user.isAdmin()) {

                Object sessionUser = session.getAttribute("user");
                if (sessionUser == null) {
                    session.setAttribute("user", user);
                }
                session.setAttribute("user", user);

                return "ADMIN";
            } else if (!user.isEnable()) {
                return "BAN";
            } else {

                Object sessionUser = session.getAttribute("user");
                if (sessionUser == null) {
                    session.setAttribute("user", user);
                }
                session.setAttribute("user", user);

                return "USER";
            }
        } else {
            return "WRONG";
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Integer logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("cart");
        return 200;
    }

    @GetMapping("/validate")
    @ResponseBody
    public Integer validate(HttpSession session) {
        User user = (User) session.getAttribute("user");
//        System.out.println(session.getId());
        if (user == null) {
            return 401;
        }
        if (user.isAdmin()) {
            return 202;
        }
        if (!user.isEnable()) {
            return 401;
        }
        return 200;
    }

    @PostMapping(value = "/avatar")
    public @ResponseBody
    String updateAvatar(HttpSession session, @RequestParam("avatar") MultipartFile file) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("login first");
        }
        try {
            Binary binary = new Binary(file.getBytes());
            userService.setUserAvatar(user.getUsername(), binary);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "update";
    }

    @GetMapping(value = "/my-avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getUserAvatar(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return userService.getUserAvatar(user.getUsername());
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getAvatar(@RequestParam("name") String username) {

        return userService.getUserAvatar(username);
    }
}
