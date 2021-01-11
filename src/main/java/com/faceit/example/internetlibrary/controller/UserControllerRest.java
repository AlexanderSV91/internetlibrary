package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.sevice.UserService;
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
    public List<User> getAllReader() {
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getAllReaderById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User addReader(@RequestBody User newReader) {
        return userService.addUser(newReader);
    }

    @DeleteMapping("/user/{id}")
    public void deleteReaderById(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/user/{id}")
    public User updateReaderById(@RequestBody User updateReader, @PathVariable Long id) {
        return userService.updateUserById(updateReader, id);
    }
}
