package com.faceit.example.internetlibrary.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
@Tag(name="Pages", description="page display")
public class WebController {

    @GetMapping("/orderbook")
    @Operation(summary = "get a page with borrowed books",
            description = "allows you to get a page with borrowed books")
    public String index() {
        return "orderbook";
    }

    @GetMapping("/user")
    @Operation(summary = "get a page with users", description = "allows you to get a page with users")
    public String reader() {
        return "user";
    }

    @GetMapping("/book")
    @Operation(summary = "get a page with books", description = "allows you to get a page with books")
    public String book() {
        return "book";
    }

    @GetMapping(value = {"/", "/login"})
    @Operation(summary = "get login page", description = "allows you to get login page")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    @Operation(summary = "get registration page", description = "allows you to get registration page")
    public String registration() {
        return "registration";
    }

    @GetMapping("/confirm/**")
    @Operation(summary = "get confirm page", description = "allows you to get confirm page")
    public String confirm() {
        return "successfulPage";
    }
}
