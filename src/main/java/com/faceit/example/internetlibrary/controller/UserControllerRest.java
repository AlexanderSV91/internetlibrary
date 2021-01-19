package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name="Users", description="interaction with users")
public class UserControllerRest {
    private final UserService userService;

    @Autowired
    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @Operation(summary = "get all users", description = "allows you to get all users")
    public List<User> getAllUserByUsername(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.getAllUserByUsername(userDetails.getUser());
    }

    @GetMapping("/current-user")
    @Operation(summary = "find user by username", description = "allows you to find user by username")
    public User findUserByUserName(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.findUserByUserName(userDetails.getUsername());
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "get a certain user", description = "allows you to get a certain user")
    public User getUserById(@PathVariable @Parameter(description = "User id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    @Operation(summary = "add new user", description = "allows you to add new user")
    public User addUser(@Valid @AuthenticationPrincipal MyUserDetails userDetails,
                        @RequestBody User newUser) {
        return userService.addUser(newUser, userDetails.getUser().getRoles());
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "delete a certain user", description = "allows you to delete a certain user")
    public void deleteUserById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @PathVariable @Parameter(description = "User id") long id) {
        userService.deleteUserById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "update a certain user", description = "allows you to update a certain user")
    public User updateUserById(@Valid @RequestBody User updateUser,
                               @PathVariable @Parameter(description = "User id") Long id) {
        return userService.updateUserById(updateUser, id);
    }
}
