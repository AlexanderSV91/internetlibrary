package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.OrderBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {
}