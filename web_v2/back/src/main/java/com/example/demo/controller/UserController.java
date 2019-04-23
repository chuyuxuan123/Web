package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
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

    @GetMapping("/registration")
    @ResponseBody
    public String addNewUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        User user = new User();
        user.setAdmin(false);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnable(true);
        userRepository.save(user);
        return "saved";
    }

    @GetMapping("/sign")
    @ResponseBody
    public String validateUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session) {

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName() + " : " + cookie.getValue());
//            }
//        }

        User user = userRepository.getByUsername(username);
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
