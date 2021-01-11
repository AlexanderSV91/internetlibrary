package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class UserControllerRest {
    private final UserService userService;

    @Autowired
    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getAllUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User newUser) {
        return userService.addUser(newUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/user/{id}")
    public User updateUserById(@RequestBody User updateUser, @PathVariable Long id) {
        return userService.updateUserById(updateUser, id);
    }
}
