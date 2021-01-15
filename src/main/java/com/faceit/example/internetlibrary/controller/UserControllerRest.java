package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<User> getAllUserByUsername(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.getAllUserByUsername(userDetails.getUser());
    }

    @GetMapping("/current-user")
    public User findUserByUserName(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.findUserByUserName(userDetails.getUsername());
    }

    @GetMapping("/user/{id}")
    public User getAllUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User addUser(@Valid @AuthenticationPrincipal MyUserDetails userDetails, @RequestBody User newUser) {
        return userService.addUser(newUser, userDetails.getUser().getRoles());
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable long id) {
        userService.deleteUserById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/user/{id}")
    public User updateUserById(@Valid @RequestBody User updateUser, @PathVariable Long id) {
        return userService.updateUserById(updateUser, id);
    }
}
