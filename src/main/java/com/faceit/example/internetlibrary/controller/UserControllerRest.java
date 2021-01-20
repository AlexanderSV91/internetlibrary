package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Users", description = "interaction with users")
public class UserControllerRest {
    private final UserService userService;

    @Autowired
    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @Operation(summary = "get all users", description = "allows you to get all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public List<User> getAllUserByUsername(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.getAllUserByUsername(userDetails.getUser());
    }

    @GetMapping("/current-user")
    @Operation(summary = "find user by username", description = "allows you to find user by username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public User findUserByUserName(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.findUserByUserName(userDetails.getUsername());
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "get a certain user", description = "allows you to get a certain user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public User getUserById(@PathVariable @Parameter(description = "User id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    @Operation(summary = "add new user", description = "allows you to add new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "user not add"),
            @ApiResponse(responseCode = "409", description = "user already exists")})
    public User addUser(@Valid @AuthenticationPrincipal MyUserDetails userDetails,
                        @RequestBody User newUser) {
        return userService.addUser(newUser, userDetails.getUser().getRoles());
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "delete a certain user", description = "allows you to delete a certain user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "role not delete")})
    public void deleteUserById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @PathVariable @Parameter(description = "User id") long id) {
        userService.deleteUserById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "update a certain user", description = "allows you to update a certain user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public User updateUserById(@Valid @RequestBody User updateUser,
                               @PathVariable @Parameter(description = "User id") Long id) {
        return userService.updateUserById(updateUser, id);
    }
}
