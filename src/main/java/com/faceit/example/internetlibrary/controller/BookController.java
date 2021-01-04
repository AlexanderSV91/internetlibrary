package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.models.Books;
import com.faceit.example.internetlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
public class BookController {
    final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Books> getAllUsers() {
        // This returns a JSON or XML with the users
        return bookRepository.findAll();
    }
}
