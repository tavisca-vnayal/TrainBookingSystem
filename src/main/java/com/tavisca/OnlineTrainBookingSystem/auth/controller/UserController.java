package com.tavisca.OnlineTrainBookingSystem.auth.controller;

import com.tavisca.OnlineTrainBookingSystem.auth.model.LoginForm;
import com.tavisca.OnlineTrainBookingSystem.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({"/", "/welcome"})
    public String welcome() {
        return "Welcome to the Online Train Reservation System by Team-MURPHY";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        return userService.checkValidityOfUser(loginForm);
    }
}