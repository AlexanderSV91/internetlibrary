package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    List<OrderBook> getOrderBookByReaderId(long idReader);
}
