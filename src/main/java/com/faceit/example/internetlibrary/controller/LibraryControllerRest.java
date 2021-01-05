package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.sevice.BookService;
import com.faceit.example.internetlibrary.sevice.OrderBookService;
import com.faceit.example.internetlibrary.sevice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class LibraryControllerRest {
    private final BookService bookService;
    private final ReaderService readerService;
    private final OrderBookService orderBookService;

    @Autowired
    public LibraryControllerRest(BookService bookService, ReaderService readerService, OrderBookService orderBookService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.orderBookService = orderBookService;
    }

    @GetMapping("/book")
    public List<Book> getAllUsers() {
        return bookService.getAllBook();
    }

    @GetMapping("/reader")
    public List<Reader> getAllReader() {
        return readerService.getAllReader();
    }

    @GetMapping("/orderbook")
    public List<OrderBook> getAllOrderBook() {
        return orderBookService.getAllOrderBook();
    }

    @GetMapping("/book/{id}")
    public Book getAllUsersById(@PathVariable long id) {
        System.out.println("getAllUsersById " + id);
        return bookService.getBookById(id);
    }

    @GetMapping("/reader/{id}")
    public Reader getAllReaderById(@PathVariable long id) {
        return readerService.getReaderById(id);
    }

    @GetMapping("/orderbook/{id}")
    public OrderBook getAllOrderBookById(@PathVariable long id) {
        return orderBookService.getOrderBookById(id);
    }

    @PostMapping("/book")
    public Book addBook(@RequestBody Book newBook) {
        return bookService.addBook(newBook);
    }

    @PostMapping("/reader")
    public Reader addReader(@RequestBody Reader newReader) {
        return readerService.addReader(newReader);
    }

    @PostMapping("/orderbook")
    public OrderBook addOrderBook(@RequestBody OrderBook newOrderBook) {
        return orderBookService.addOrderBook(newOrderBook);
    }
}
