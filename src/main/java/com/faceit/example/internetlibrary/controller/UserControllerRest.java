package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.UserRequest;
import com.faceit.example.internetlibrary.dto.response.UserResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.UserMapper;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Users", description = "interaction with users")
@SecurityRequirement(name = "basic")
public class UserControllerRest {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserControllerRest(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/user")
    @Operation(summary = "get all users", description = "allows you to get all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "users not found")})
    public List<UserResponse> getAllUserByUsername(@AuthenticationPrincipal MyUserDetails userDetails) {
        List<User> users = userService.getAllUserByUsername(userDetails.getUser());
        if (users != null) {
            return userMapper.usersToUsersResponse(users);
        }
        throw new ResourceNotFoundException("exception.notFound");
    }

    @GetMapping("/current-user")
    @Operation(summary = "find user by username", description = "allows you to find user by username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public UserResponse findUserByUserName(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userMapper.userToUserResponse(userService.findUserByUserName(userDetails.getUsername()));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "get a certain user", description = "allows you to get a certain user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public UserResponse getUserById(@PathVariable @Parameter(description = "User id") long id) {
        return userMapper.userToUserResponse(userService.getUserById(id));
    }

    @PostMapping("/user")
    @Operation(summary = "add new user", description = "allows you to add new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "user created",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "user not add"),
            @ApiResponse(responseCode = "409", description = "user already exists")})
    public UserResponse addUser(@AuthenticationPrincipal MyUserDetails userDetails,
                        @Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        return userMapper.userToUserResponse(userService.addUser(user, userDetails.getUser().getRoles()));
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "delete a certain user", description = "allows you to delete a certain user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "user not delete")})
    public void deleteUserById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @PathVariable @Parameter(description = "User id") long id) {
        userService.deleteUserById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/user/{id}")
    @Operation(summary = "update a certain user", description = "allows you to update a certain user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "user not found")})
    public UserResponse updateUserById(@Valid @RequestBody UserRequest userRequest,
                               @PathVariable @Parameter(description = "User id") Long id) {
        return userMapper.userToUserResponse(userService.updateUserById(userRequest, id));
    }
}
