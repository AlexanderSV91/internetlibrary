package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.request.mysql.OrderBookRequest;
import com.faceit.example.internetlibrary.dto.request.mysql.UserRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.dto.response.mysql.OrderBookResponse;
import com.faceit.example.internetlibrary.dto.response.mysql.UserResponse;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.faceit.example.internetlibrary.model.mysql.*;
import com.faceit.example.internetlibrary.service.mysql.OrderBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderBookControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderBookService orderBookService;

    @Test
    @DisplayName("GET /api/orderbook success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void getOrderBooksByUserUserName() throws Exception {
        User userAuth = getUserAuth();
        Page<OrderBook> returnPage = getPageOrderBook();

        doReturn(returnPage).when(orderBookService).findOrderBooksByUserUserName(isA(Pageable.class), isA(User.class));

        mockMvc.perform(get("/api/orderbook")
                .with(user(new MyUserDetails(userAuth)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status", is("NEW")))
                .andExpect(jsonPath("$.content[0].user.userName", is(returnPage.getContent().get(0).getUser().getUserName())))
                .andExpect(jsonPath("$.content[0].user.firstName", is(returnPage.getContent().get(0).getUser().getFirstName())))
                .andExpect(jsonPath("$.content[0].user.lastName", is(returnPage.getContent().get(0).getUser().getLastName())))
                .andExpect(jsonPath("$.content[0].user.email", is(returnPage.getContent().get(0).getUser().getEmail())))
                .andExpect(jsonPath("$.content[0].user.age", is(returnPage.getContent().get(0).getUser().getAge())))
                .andExpect(jsonPath("$.content[0].book.name", is(returnPage.getContent().get(0).getBook().getName())))
                .andExpect(jsonPath("$.content[0].book.bookCondition", is(returnPage.getContent().get(0).getBook().getBookCondition())))
                .andExpect(jsonPath("$.content[0].book.description", is(returnPage.getContent().get(0).getBook().getDescription())))
                .andExpect(jsonPath("$.content[0].note", is("note")));
    }

    @Test
    @DisplayName("GET /api/orderbook/{id} success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void getAllOrderBookById() throws Exception {
        OrderBookResponse orderBookResponse = getOrderBookResponse();
        OrderBook orderBook = getOrderBook();

        when(orderBookService.getOrderBookById(1)).thenReturn(orderBook);
        mockMvc.perform(get("/api/orderbook/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("NEW")))
                .andExpect(jsonPath("$.user.userName", is(orderBookResponse.getUser().getUserName())))
                .andExpect(jsonPath("$.user.firstName", is(orderBookResponse.getUser().getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(orderBookResponse.getUser().getLastName())))
                .andExpect(jsonPath("$.user.email", is(orderBookResponse.getUser().getEmail())))
                .andExpect(jsonPath("$.user.age", is(orderBookResponse.getUser().getAge())))
                .andExpect(jsonPath("$.book.name", is(orderBookResponse.getBook().getName())))
                .andExpect(jsonPath("$.book.bookCondition", is(orderBookResponse.getBook().getBookCondition())))
                .andExpect(jsonPath("$.book.description", is(orderBookResponse.getBook().getDescription())))
                .andExpect(jsonPath("$.note", is("note")));
    }

    @Test
    @DisplayName("GET /api/orderbook/user/{id} success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void getOrderBookByReader() throws Exception {
        List<OrderBookResponse> orderBookResponses = Lists.newArrayList(getOrderBookResponse());
        List<OrderBook> orderBooks = Lists.newArrayList(getOrderBook());

        when(orderBookService.getOrderBookByReaderId(1)).thenReturn(orderBooks);
        mockMvc.perform(get("/api/orderbook/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("NEW")))
                .andExpect(jsonPath("$[0].user.userName", is(orderBookResponses.get(0).getUser().getUserName())))
                .andExpect(jsonPath("$[0].user.firstName", is(orderBookResponses.get(0).getUser().getFirstName())))
                .andExpect(jsonPath("$[0].user.lastName", is(orderBookResponses.get(0).getUser().getLastName())))
                .andExpect(jsonPath("$[0].user.email", is(orderBookResponses.get(0).getUser().getEmail())))
                .andExpect(jsonPath("$[0].user.age", is(orderBookResponses.get(0).getUser().getAge())))
                .andExpect(jsonPath("$[0].book.name", is(orderBookResponses.get(0).getBook().getName())))
                .andExpect(jsonPath("$[0].book.bookCondition", is(orderBookResponses.get(0).getBook().getBookCondition())))
                .andExpect(jsonPath("$[0].book.description", is(orderBookResponses.get(0).getBook().getDescription())))
                .andExpect(jsonPath("$[0].note", is("note")));
    }

    @Test
    @DisplayName("POST /api/orderbook success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void addOrderBook() throws Exception {
        User user = getUserAuth();
        OrderBook orderBook = getOrderBook();
        OrderBook orderBookReturn = getOrderBook();
        OrderBookRequest orderBookRequest = getOrderBookRequest();
        OrderBookResponse orderBookResponse = getOrderBookResponse();

        doReturn(orderBookReturn).when(orderBookService).addOrderBook(isA(OrderBook.class));

        mockMvc.perform(post("/api/orderbook")
                .with(user(new MyUserDetails(user)))
                .content(asJsonString(orderBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(orderBookResponse)));
    }

    @Test
    @DisplayName("PUT /api/orderbook/{id} success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void updateReaderById() throws Exception {
        User user = getUserAuth();
        OrderBook orderBookReturn = getOrderBook();
        OrderBookRequest orderBookRequest = getOrderBookRequest();
        OrderBookResponse orderBookResponse = getOrderBookResponse();

        doReturn(orderBookReturn).when(orderBookService).updateOrderBookById(orderBookRequest, 1L);

        mockMvc.perform(put("/api/orderbook/{id}", 1L)
                .with(user(new MyUserDetails(user)))
                .content(asJsonString(orderBookReturn))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(orderBookResponse)));
    }

    static OrderBook getOrderBook() {
        OrderBook orderBook = new OrderBook();
        orderBook.setUser(getUser());
        orderBook.setBook(getBook());
        orderBook.setNote("note");
        orderBook.setStatus(Status.NEW);

        LocalDateTime dateTime = LocalDateTime.of(2021,2,3,15,3);
        orderBook.setStartDate(dateTime);
        orderBook.setEndDate(dateTime);
        return orderBook;
    }

    static OrderBookResponse getOrderBookResponse() {
        OrderBookResponse orderBookResponse = new OrderBookResponse();
        orderBookResponse.setUser(getUserResponse());
        orderBookResponse.setBook(getBookResponse());
        orderBookResponse.setNote("note");
        orderBookResponse.setStatus(Status.NEW);

        LocalDateTime dateTime = LocalDateTime.of(2021,2,3,15,3);
        orderBookResponse.setStartDate(dateTime);
        orderBookResponse.setEndDate(dateTime);
        return orderBookResponse;
    }

    static OrderBookRequest getOrderBookRequest() {
        OrderBookRequest orderBookRequest = new OrderBookRequest();
        orderBookRequest.setUser(getUserResponse());
        orderBookRequest.setBook(getBookResponse());
        orderBookRequest.setNote("note");
        orderBookRequest.setStatus(Status.NEW);

        LocalDateTime dateTime = LocalDateTime.of(2021,2,3,15,3);
        orderBookRequest.setStartDate(dateTime);
        orderBookRequest.setEndDate(dateTime);
        return orderBookRequest;
    }

    static User getUserAuth() {
        NumberAuthorization numberAuthorization = new NumberAuthorization(1, 3, LocalDateTime.now());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, "ROLE_EMPLOYEE"));
        return new User(1, "123456", "123456", "123456", "123456", "1@1.com", 1, true, roleSet, numberAuthorization);
    }

    private static UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1);
        userResponse.setUserName("123456");
        userResponse.setFirstName("123456");
        userResponse.setLastName("123456");
        userResponse.setEmail("1@1.com");
        userResponse.setAge(1);
        return userResponse;
    }

    private static User getUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("123456");
        user.setPassword("123456");
        user.setFirstName("123456");
        user.setLastName("123456");
        user.setEmail("1@1.com");
        user.setAge(1);
        return user;
    }

    private static UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("123456");
        userRequest.setFirstName("123456");
        userRequest.setLastName("123456");
        userRequest.setEmail("1@1.com");
        userRequest.setAge(1);
        return userRequest;
    }

    private static Book getBook() {
        Book book = new Book();
        book.setId(1);
        book.setName("name1");
        book.setBookCondition("bookCondition1");
        book.setDescription("description1");
        return book;
    }

    private static BookRequest getBookRequest() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("name1");
        bookRequest.setBookCondition("bookCondition1");
        bookRequest.setDescription("description1");
        return bookRequest;
    }

    private static BookResponse getBookResponse() {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(1);
        bookResponse.setName("name1");
        bookResponse.setBookCondition("bookCondition1");
        bookResponse.setDescription("description1");
        return bookResponse;
    }


    private static Page<OrderBook> getPageOrderBook() {
        List<OrderBook> list = Lists.newArrayList(getOrderBook());
        return new PageImpl<>(list, PageRequest.of(0, 10), list.size());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}