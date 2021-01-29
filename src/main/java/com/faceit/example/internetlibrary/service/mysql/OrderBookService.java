package com.faceit.example.internetlibrary.service.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.OrderBookRequest;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.OrderBook;
import com.faceit.example.internetlibrary.model.mysql.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderBookService {

    Page<OrderBook> getPagingOrderBook(Pageable pageable);

    List<OrderBook> getAllOrderBook();

    OrderBook getOrderBookById(long id);

    OrderBook addOrderBook(OrderBook newOrderBook);

    OrderBook updateOrderBookById(OrderBookRequest orderBookRequest, long id);

    void deleteOrderBookById(long id, long idUser);

    List<OrderBook> getOrderBookByReaderId(long idReader);

    Page<OrderBook> findOrderBooksByUserUserName(Pageable pageable, User user);

    Status[] getAllStatus();
}
