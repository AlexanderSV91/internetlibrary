package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.enums.Status;
import com.faceit.example.internetlibrary.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<OrderBook> getOrderBooksByUserUserName(Principal principal) {
        return orderBookService.findOrderBooksByUserUserName(principal.getName());
    }

    @GetMapping("/orderbook/status")
    public Status[] getAllStatus() {
        return orderBookService.getAllStatus();
    }

    @GetMapping("/orderbook/{id}")
    public OrderBook getAllOrderBookById(@PathVariable long id) {
        return orderBookService.getOrderBookById(id);
    }

    @GetMapping("orderbook/user/{id}")
    public List<OrderBook> getOrderBookByReader(@PathVariable long id) {
        return orderBookService.getOrderBookByReaderId(id);
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
