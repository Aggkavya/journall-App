package com.kavya.journalApp.controller;

import com.kavya.journalApp.entity.User;
import com.kavya.journalApp.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class HealthCheck {

    private final UserService userService;
    public HealthCheck(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User newUser){
        userService.saveEntry(newUser);
    }

    @GetMapping("/health-check")
    public String healthcheck(){
        return "ok";
    }
}
