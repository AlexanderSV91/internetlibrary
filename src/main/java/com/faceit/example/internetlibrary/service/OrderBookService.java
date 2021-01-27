package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.dto.request.OrderBookRequest;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.model.enumeration.Status;

import java.util.List;

public interface OrderBookService {
    List<OrderBook> getAllOrderBook();

    OrderBook getOrderBookById(long id);

    OrderBook addOrderBook(OrderBook newOrderBook);

    OrderBook updateOrderBookById(OrderBookRequest orderBookRequest, long id);

    void deleteOrderBookById(long id);

    List<OrderBook> getOrderBookByReaderId(long idReader);

    List<OrderBook> findOrderBooksByUserUserName(User user);

    Status[] getAllStatus();
}
