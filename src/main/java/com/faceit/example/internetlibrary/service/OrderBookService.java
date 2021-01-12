package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.enums.Status;

import java.util.List;

public interface OrderBookService {
    List<OrderBook> getAllOrderBook();

    OrderBook getOrderBookById(long id);

    OrderBook addOrderBook(OrderBook newOrderBook);

    OrderBook updateOrderBookById(OrderBook updateOrderBook, long id);

    void deleteOrderBookById(long id);

    List<OrderBook> getOrderBookByReaderId(long idReader);

    List<OrderBook> findOrderBooksByUserUserName(String username);

    Status[] getAllStatus();
}
