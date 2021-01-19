package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.service.OrderBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name="Order book", description="interaction with order books")
public class OrderBookControllerRest {
    private final OrderBookService orderBookService;

    @Autowired
    public OrderBookControllerRest(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @GetMapping("/orderbook")
    @Operation(summary = "get all borrowed books",
            description = "allows you to receive all borrowed books in the library")
    public List<OrderBook> getOrderBooksByUserUserName(@AuthenticationPrincipal MyUserDetails userDetails) {
        return orderBookService.findOrderBooksByUserUserName(userDetails.getUser());
    }

    @GetMapping("/orderbook/status")
    @Operation(summary = "get all status",
            description = "allows you to get all statuses for books in the library")
    public Status[] getAllStatus() {
        return orderBookService.getAllStatus();
    }

    @GetMapping("/orderbook/{id}")
    @Operation(summary = "get a certain book",
            description = "allows you to get information about a specific book in the library")
    public OrderBook getAllOrderBookById(@PathVariable @Parameter(name = "Borrowed book id") long id) {
        return orderBookService.getOrderBookById(id);
    }

    @GetMapping("orderbook/user/{id}")
    @Operation(summary = "get all the books taken by the user",
            description = "allows you to get all the books taken by the user to the library")
    public List<OrderBook> getOrderBookByReader(@PathVariable @Parameter(name = "User id") long id) {
        return orderBookService.getOrderBookByReaderId(id);
    }

    @PostMapping("/orderbook")
    @Operation(summary = "add the book the user took",
            description = "allows you to add a book that the user has taken from the library")
    public OrderBook addOrderBook(@RequestBody OrderBook newOrderBook) {
        return orderBookService.addOrderBook(newOrderBook);
    }

    @DeleteMapping("/orderbook/{id}")
    @Operation(summary = "delete the book the user took",
            description = "allows you to delete the book the user has taken from the library")
    public void deleteOrderBookById(@PathVariable @Parameter(name = "Borrowed book id") long id) {
        orderBookService.deleteOrderBookById(id);
    }

    @PutMapping("/orderbook/{id}")
    @Operation(summary = "update the data on the book taken",
            description = "allows you to update data about a book taken from the library")
    public OrderBook updateReaderById(@RequestBody OrderBook updateOrderBook,
                                      @PathVariable @Parameter(name = "Borrowed book id") Long id) {
        return orderBookService.updateOrderBookById(updateOrderBook, id);
    }
}
