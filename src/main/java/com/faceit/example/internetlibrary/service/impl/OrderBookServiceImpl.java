package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.Utils;
import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.repository.OrderBookRepository;
import com.faceit.example.internetlibrary.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderBookServiceImpl implements OrderBookService {
    private final OrderBookRepository orderBookRepository;

    @Autowired
    public OrderBookServiceImpl(OrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }

    @Override
    public List<OrderBook> getAllOrderBook() {
        return orderBookRepository.findAll();
    }

    @Override
    public OrderBook getOrderBookById(long id) {
        Optional<OrderBook> optionalOrderBook = orderBookRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalOrderBook);
    }

    @Override
    public OrderBook addOrderBook(OrderBook newOrderBook) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        if (newOrderBook.getStartDate() == null) {
            newOrderBook.setStartDate(now);
        }
        if (newOrderBook.getEndDate() == null) {
            newOrderBook.setEndDate(now);
        }
        if (newOrderBook.getStartDate().isAfter(newOrderBook.getEndDate()) ||
                newOrderBook.getStartDate().isEqual(newOrderBook.getEndDate())) {
            newOrderBook.setEndDate(now.plusMonths(2L));
            newOrderBook.setStartDate(now);
        }
        return orderBookRepository.save(newOrderBook);
    }

    @Override
    public OrderBook updateOrderBookById(OrderBook updateOrderBook, long id) {
        OrderBook orderBook = getOrderBookById(id);
        if (orderBook != null) {
            updateOrderBook.setId(id);
        }
        orderBookRepository.save(updateOrderBook);
        return updateOrderBook;
    }

    @Override
    public void deleteOrderBookById(long id) {
        orderBookRepository.deleteById(id);
    }

    @Override
    public List<OrderBook> getOrderBookByReaderId(long idReader) {
        return orderBookRepository.getOrderBookByUserId(idReader);
    }

    @Override
    public List<OrderBook> findOrderBooksByUserUserName(User user) {
        boolean isEmployee = Utils.isEmployee(user.getRoles());
        List<OrderBook> orderBookList;
        if (isEmployee) {
            orderBookList = getAllOrderBook();
        } else {
            orderBookList = orderBookRepository.findOrderBooksByUserUserName(user.getUserName());
        }
        return orderBookList;
    }

    @Override
    public Status[] getAllStatus() {
        return Status.values();
    }
}
