package com.faceit.example.internetlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

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

/*    @GetMapping("/login")
    public String login() {
        return "login";
    }*/

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
}
