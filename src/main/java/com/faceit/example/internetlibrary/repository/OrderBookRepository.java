package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.models.OrderBook;
import org.springframework.data.repository.CrudRepository;

public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {
}
