package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api-public"})
@Tag(name="Registration", description="registration and verification of users")
public class RegistrationControllerRest {
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public RegistrationControllerRest(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @PostMapping("/registration")
    @Operation(summary = "User registration", description = "Allows you to register a user")
    public void addUser(@Valid @RequestBody User newUser) {
        confirmationTokenService.addConfirmationToken(newUser);
    }

    @GetMapping("/confirm/{token}")
    @Operation(summary = "Email verification", description = "Allows you to verification email users")
    public TokenStatus confirmMail(@PathVariable @Parameter(description = "verification token") String token) {
        return confirmationTokenService.findByToken(token);
    }
}
