package com.faceit.example.internetlibrary.service.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.OrderBookRequest;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.model.mysql.OrderBook;
import com.faceit.example.internetlibrary.model.mysql.User;

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
