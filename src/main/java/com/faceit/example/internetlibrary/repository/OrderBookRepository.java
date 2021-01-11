package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    List<OrderBook> getOrderBookByUserId(long idReader);
    List<OrderBook> findOrderBooksByUser_UserName(String username);
}
