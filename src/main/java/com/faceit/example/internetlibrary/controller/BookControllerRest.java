package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.sevice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Book addBook(@RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBookById(@PathVariable long id) {
        bookService.deleteBookById(id);
    }

    @PutMapping("/book/{id}")
    public Book updateBookById(@RequestBody Book updateBook, @PathVariable Long id) {
        return bookService.updateBookById(updateBook, id);
    }
}
