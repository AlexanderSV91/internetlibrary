package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.NumberAuthorization;
import com.faceit.example.internetlibrary.model.mysql.Role;
import com.faceit.example.internetlibrary.model.mysql.User;
import com.faceit.example.internetlibrary.service.mysql.BookService;
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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerRestTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/book success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void testGetAllBook() throws Exception {
        Book book1 = new Book(1L, "name1", "bookCondition1", "description1");
        Book book2 = new Book(2L, "name2", "bookCondition2", "description2");
        List<Book> list = Lists.newArrayList(book1, book2);
        Page<Book> returnPage = new PageImpl<>(list, PageRequest.of(0, 10), list.size());

        doReturn(returnPage).when(bookService).getPagingBook(isA(Pageable.class));
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(returnPage)));
    }

    @Test
    @DisplayName("GET /api/book/{id} success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void testGetBookById() throws Exception {
        Book book = getBook();
        book.setId(1);
        BookResponse bookResponse = getBookResponse();
        when(bookService.getBookById(1)).thenReturn(book);
        mockMvc.perform(get("/api/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(bookResponse)));
    }

    @Test
    @DisplayName("POST /api/book success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void testAddBook() throws Exception {
        User user = getUserAuth();
        Book bookToPost = getBook();
        BookResponse bookResponse = getBookResponse();
        Book bookToReturn = getBook();
        bookToReturn.setId(1);

        doReturn(bookToReturn).when(bookService).addBook(bookToPost, user.getRoles());

        mockMvc.perform(post("/api/book")
                .with(user(new MyUserDetails(user)))
                .content(asJsonString(bookToPost))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(bookResponse)));
    }

    @Test
    @DisplayName("PUT /api/book/{id} success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void updateBookById() throws Exception {
        User user = getUserAuth();
        Book bookToReturn = getBook();
        bookToReturn.setId(1);
        BookResponse bookResponse = getBookResponse();
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("name1");
        bookRequest.setBookCondition("bookCondition1");
        bookRequest.setDescription("description1");

        doReturn(bookToReturn).when(bookService).updateBookById(bookRequest, 1L, user.getRoles());

        mockMvc.perform(put("/api/book/{id}", 1L)
                .with(user(new MyUserDetails(user)))
                .content(asJsonString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(bookResponse)));
    }

    private static Book getBook() {
        Book book = new Book();
        book.setName("name1");
        book.setBookCondition("bookCondition1");
        book.setDescription("description1");
        return book;
    }

    private static BookResponse getBookResponse() {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(1);
        bookResponse.setName("name1");
        bookResponse.setBookCondition("bookCondition1");
        bookResponse.setDescription("description1");
        return bookResponse;
    }

    private static User getUserAuth() {
        NumberAuthorization numberAuthorization = new NumberAuthorization(1, 3, LocalDateTime.now());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, "ROLE_EMPLOYEE"));
        return new User(1, "123456", "123456", "123456", "123456", "1@1.com", 1, true, roleSet, numberAuthorization);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}