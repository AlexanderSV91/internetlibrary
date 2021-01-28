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
