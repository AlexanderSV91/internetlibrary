package com.faceit.example.internetlibrary.sevice;

import com.faceit.example.internetlibrary.model.OrderBook;

import java.util.List;

public interface OrderBookService {
    List<OrderBook> getAllOrderBook();

    OrderBook getOrderBookById(long id);

    OrderBook addOrderBook(OrderBook newOrderBook);
}
