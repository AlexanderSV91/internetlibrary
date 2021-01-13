package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.RoleService;
import com.faceit.example.internetlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class RegistrationControllerRest {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationControllerRest(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration-role")
    public List<Role> getAllRole() {
        List<Role> roles = roleService.getAllRole();
        System.out.println(roles);
        return roles;
    }

    @PostMapping("/registration")
    public User addUser(@RequestBody User newUser) {
        System.out.println(newUser);
        return userService.addUser(newUser);
    }
}
