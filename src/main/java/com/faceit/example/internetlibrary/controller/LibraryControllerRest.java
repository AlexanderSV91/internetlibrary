package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.repository.BookRepository;
import com.faceit.example.internetlibrary.repository.OrderBookRepository;
import com.faceit.example.internetlibrary.repository.ReaderRepository;
import com.faceit.example.internetlibrary.sevice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Controller
//@RequestMapping(value = {"/api"}, produces = {"application/json"})
@RequestMapping(value = {"/api"})
public class LibraryControllerRest {
    private final BookRepository bookRepository;
    private final BookService bookService;


    private final ReaderRepository readerRepository;
    private final OrderBookRepository orderBookRepository;

    @Autowired
    public LibraryControllerRest(BookService bookService, BookRepository bookRepository, ReaderRepository readerRepository, OrderBookRepository orderBookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;


        this.readerRepository = readerRepository;
        this.orderBookRepository = orderBookRepository;
    }

    @GetMapping(path = "/book")
    public List<Book> getAllUsers() {
        return bookService.getAllBook();
    }

/*    @GetMapping(path = "/book")
    public Iterable<Book> getAllUsers() {
        return bookRepository.findAll();
    }*/

    @GetMapping(path = "/reader")
    public Iterable<Reader> getAllReader() {
        return readerRepository.findAll();
    }

    @GetMapping(path = "/orderbook")
    public Iterable<OrderBook> getAllOrderBook(Model model) {
        return orderBookRepository.findAll();
    }

/*    @GetMapping(path = "/book")
    public String getAllBook(Model model) {
        Iterable<Book> booksList = bookRepository.findAll();
        model.addAttribute("booksList", booksList);
        return "boolAll";
    }

    @GetMapping(path = "/reader")
    public String getAllReader(Model model) {
        Iterable<Reader> readersList = readerRepository.findAll();
        model.addAttribute("readersList", readersList);
        return "readerAll";
    }

    @GetMapping(path = "/orderbook")
    public String getAllOrderBook(Model model) {
        Iterable<OrderBook> orderBookList = orderBookRepository.findAll();
        for (OrderBook order: orderBookList) {
            System.out.println(order.toString());
        }
        model.addAttribute("orderBookList", orderBookList);
        return "orderBook";
    }*/
}
