package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.sevice.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class OrderBookControllerRest {
    private final OrderBookService orderBookService;

    @Autowired
    public OrderBookControllerRest(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @GetMapping("/orderbook")
    public List<OrderBook> getAllOrderBook() {
        return orderBookService.getAllOrderBook();
    }

    @GetMapping("/orderbook/{id}")
    public OrderBook getAllOrderBookById(@PathVariable long id) {
        return orderBookService.getOrderBookById(id);
    }

    @GetMapping("orderbook/reader/{id}")
    public List<OrderBook> getOrderBookByReader(@PathVariable long id) {
        System.out.println(id);
        List<OrderBook> orderBookList = orderBookService.getOrderBookByReaderId(id);
        System.out.println(orderBookList);
        return orderBookList;
    }

    @PostMapping("/orderbook")
    public OrderBook addOrderBook(@RequestBody OrderBook newOrderBook) {
        return orderBookService.addOrderBook(newOrderBook);
    }

    @DeleteMapping("/orderbook/{id}")
    public void deleteOrderBookById(@PathVariable long id) {
        orderBookService.deleteOrderBookById(id);
    }

    @PutMapping("/orderbook/{id}")
    public OrderBook updateReaderById(@RequestBody OrderBook updateOrderBook, @PathVariable Long id) {
        return orderBookService.updateOrderBookById(updateOrderBook, id);
    }
}
