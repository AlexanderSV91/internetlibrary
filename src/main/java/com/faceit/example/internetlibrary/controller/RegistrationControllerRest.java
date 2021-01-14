package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api-public"})
public class RegistrationControllerRest {
    private final UserService userService;

    @Autowired
    public RegistrationControllerRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public User addUser(@Valid @RequestBody User newUser) {
        return userService.addUser(newUser);
    }
}
