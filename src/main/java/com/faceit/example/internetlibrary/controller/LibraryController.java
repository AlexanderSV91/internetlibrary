package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.models.Books;
import com.faceit.example.internetlibrary.models.OrderBook;
import com.faceit.example.internetlibrary.models.Readers;
import com.faceit.example.internetlibrary.repository.BookRepository;
import com.faceit.example.internetlibrary.repository.OrderBookRepository;
import com.faceit.example.internetlibrary.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class LibraryController {
    final BookRepository bookRepository;
    final ReaderRepository readerRepository;
    final OrderBookRepository orderBookRepository;

    @Autowired
    public LibraryController(BookRepository bookRepository, ReaderRepository readerRepository, OrderBookRepository orderBookRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.orderBookRepository = orderBookRepository;
    }

    /*@GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Books> getAllUsers() {
        // This returns a JSON or XML with the users
        return bookRepository.findAll();
    }*/

    @GetMapping(path = "/book")
    public String getAllBook(Model model) {
        Iterable<Books> booksList = bookRepository.findAll();
        model.addAttribute("booksList", booksList);
        return "boolAll";
    }

    @GetMapping(path = "/reader")
    public String getAllReader(Model model) {
        Iterable<Readers> readersList = readerRepository.findAll();
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
    }
}
