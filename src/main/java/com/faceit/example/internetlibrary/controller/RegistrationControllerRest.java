package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api-public"})
public class RegistrationControllerRest {
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public RegistrationControllerRest(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @PostMapping("/registration")
    public void addUser(@Valid @RequestBody User newUser) {
        confirmationTokenService.addConfirmationToken(newUser);
    }

/*    @GetMapping("/confirm/{token}")
    public boolean confirmMail(@PathVariable String token) {
        boolean isVerified = registrationService.findByToken(token);
        return isVerified;
    }*/
}
