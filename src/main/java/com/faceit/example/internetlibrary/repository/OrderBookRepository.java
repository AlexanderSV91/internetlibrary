package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
