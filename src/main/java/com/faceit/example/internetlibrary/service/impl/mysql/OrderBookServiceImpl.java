package com.faceit.example.internetlibrary.service.impl.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.OrderBookRequest;
import com.faceit.example.internetlibrary.mapper.mysql.OrderBookMapper;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.OrderBook;
import com.faceit.example.internetlibrary.model.mysql.User;
import com.faceit.example.internetlibrary.repository.mysql.OrderBookRepository;
import com.faceit.example.internetlibrary.service.mysql.BookService;
import com.faceit.example.internetlibrary.service.mysql.OrderBookService;
import com.faceit.example.internetlibrary.service.mysql.UserService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final OrderBookMapper orderBookMapper;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public OrderBookServiceImpl(OrderBookRepository orderBookRepository,
                                OrderBookMapper orderBookMapper,
                                UserService userService,
                                BookService bookService) {
        this.orderBookRepository = orderBookRepository;
        this.orderBookMapper = orderBookMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public Page<OrderBook> getPagingOrderBook(Pageable pageable) {
        return orderBookRepository.findAll(pageable);
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
    @CacheEvict(value = "orderbooks", key = "#newOrderBook.user.id", allEntries = true)
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
    @CacheEvict(value = "orderbooks", key = "#orderBookRequest.user.id", allEntries = true)
    public OrderBook updateOrderBookById(OrderBookRequest orderBookRequest, long id) {
        Book book = bookService.getBookById(orderBookRequest.getBook().getId());
        User user = userService.getUserById(orderBookRequest.getUser().getId());

        OrderBook updateOrderBook = orderBookMapper
                .updateOrderBookFromOrderBookRequest(orderBookRequest, getOrderBookById(id));
        updateOrderBook.setBook(book);
        updateOrderBook.setUser(user);

        return orderBookRepository.save(updateOrderBook);
    }

    @Override
    @CacheEvict(value = "orderbooks", key = "#idUser", allEntries = true)
    public void deleteOrderBookById(long id, long idUser) {
        orderBookRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "orderbooks", key = "#idReader")
    public List<OrderBook> getOrderBookByReaderId(long idReader) {
        return orderBookRepository.getOrderBookByUserId(idReader);
    }

    @Override
    @Cacheable(value = "orderbooks", key = "{#user.id, #pageable.pageNumber}")
    public Page<OrderBook> findOrderBooksByUserUserName(Pageable pageable, User user) {
        boolean isEmployee = Utils.isEmployee(user.getRoles());
        Page<OrderBook> orderBookList;
        if (isEmployee) {
            orderBookList = getPagingOrderBook(pageable);
        } else {
            orderBookList = orderBookRepository.findOrderBooksByUserUserName(user.getUserName(), pageable);
        }
        return orderBookList;
    }

    @Override
    public Status[] getAllStatus() {
        return Status.values();
    }
}
