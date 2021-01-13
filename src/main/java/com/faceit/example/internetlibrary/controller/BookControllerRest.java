package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.config.MyUserDetails;
import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class BookControllerRest {
    private final BookService bookService;

    @Autowired
    public BookControllerRest(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/book")
    public Book addBook(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody Book newBook) {
        return bookService.addBook(newBook, userDetails.getUser().getRoles());
    }

    @DeleteMapping("/book/{id}")
    public void deleteBookById(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable long id) {
        bookService.deleteBookById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/book/{id}")
    public Book updateBookById(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody Book updateBook, @PathVariable Long id) {
        return bookService.updateBookById(updateBook, id, userDetails.getUser().getRoles());
    }
}
