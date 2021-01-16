package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public WebController(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("/orderbook")
    public String index() {
        return "orderbook";
    }

    @GetMapping("/user")
    public String reader() {
        return "user";
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/api-public/confirm/**")
    public String confirm() {
        return "login";
    }

    @GetMapping("/api-public/confirm/{token}")
    public
    String confirm(@PathVariable String token) {
        boolean isVerified = confirmationTokenService.findByToken(token);
        if (isVerified) {
            return "login";
        }
        return null;
    }
}
