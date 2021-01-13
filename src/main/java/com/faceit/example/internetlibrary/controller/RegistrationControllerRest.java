package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
public class RegistrationControllerRest {
    private final UserService userService;

    public RegistrationControllerRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public User addUser(@RequestBody User newUser) {
        System.out.println(newUser);
        return userService.addUser(newUser);
    }
}
