package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.repository.BookRepository;
import com.faceit.example.internetlibrary.repository.OrderBookRepository;
import com.faceit.example.internetlibrary.repository.ReaderRepository;
import com.faceit.example.internetlibrary.sevice.BookService;
import com.faceit.example.internetlibrary.sevice.OrderBookService;
import com.faceit.example.internetlibrary.sevice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/book")
    public List<Book> getAllUsers() {
        return bookService.getAllBook();
    }

    @GetMapping(path = "/reader")
    public List<Reader> getAllReader() {
        return readerService.getAllReader();
    }

    @GetMapping(path = "/orderbook")
    public List<OrderBook> getAllOrderBook() {
        return orderBookService.getAllOrderBook();
    }

    @GetMapping(path = "/book/{id}")
    public Book getAllUsersById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping(path = "/reader/{id}")
    public Reader getAllReaderById(@PathVariable long id) {
        return readerService.getReaderById(id);
    }

    @GetMapping(path = "/orderbook/{id}")
    public OrderBook getAllOrderBookById(@PathVariable long id) {
        return orderBookService.getOrderBookById(id);
    }
}
