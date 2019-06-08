package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

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
}
