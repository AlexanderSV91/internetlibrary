package com.faceit.example.internetlibrary.sevice;

import com.faceit.example.internetlibrary.model.OrderBook;
import com.faceit.example.internetlibrary.repository.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        OrderBook orderBook = null;
        Optional<OrderBook> optionalOrderBook = orderBookRepository.findById(id);
        if (optionalOrderBook.isPresent()) {
            orderBook = optionalOrderBook.get();
        }
        return orderBook;
    }

    @Override
    public OrderBook addOrderBook(OrderBook newOrderBook) {
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
    public List<OrderBook> getOrderBookByReader(long idReader) {
        return orderBookRepository.getOrderBookByReader(idReader);
    }
}
