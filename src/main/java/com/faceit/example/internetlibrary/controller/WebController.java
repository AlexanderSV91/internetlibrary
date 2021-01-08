package com.faceit.example.internetlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/reader")
    public String reader() {
        return "reader";
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }
}
