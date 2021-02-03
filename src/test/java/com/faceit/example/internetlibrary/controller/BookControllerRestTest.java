package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
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
        Book book = new Book(1L, "name1", "bookCondition1", "description1");
        when(bookService.getBookById(1)).thenReturn(book);
        mockMvc.perform(get("/api/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(book)));
    }

    @Test
    @DisplayName("POST /api/book success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void testAddBook() throws Exception {
        Book bookToPost = new Book();
        bookToPost.setName("name11");
        bookToPost.setBookCondition("bookCondition11");
        bookToPost.setDescription("description11");
        Book bookToReturn = new Book(1L, "name11", "bookCondition11", "description11");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, "ROLE_EMPLOYEE"));
        User user = new User(1, "1", "1", "1", "1", "1@1.com", 1, true, roleSet, new NumberAuthorization());
        MyUserDetails myUserDetails = new MyUserDetails(user);

        doReturn(bookToReturn).when(bookService).addBook(bookToPost, roleSet);

        mockMvc.perform(post("/api/book")
                .with(user(myUserDetails))
                .content(asJsonString(bookToPost))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(bookToReturn)));
    }

    @Test
    @DisplayName("PUT /api/book/{id} success")
    @WithMockUser(value = "1", username = "1", password = "1", roles = "EMPLOYEE")
    void updateBookById() throws Exception {
        BookRequest bookToPut = new BookRequest();
        bookToPut.setName("name11");
        bookToPut.setBookCondition("bookCondition11");
        bookToPut.setDescription("description11");
        Book bookToReturn = new Book(1L, "name1", "bookCondition1", "description1");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1, "ROLE_EMPLOYEE"));
        User user = new User(1, "1", "1", "1", "1", "1@1.com", 1, true, roleSet, new NumberAuthorization());
        MyUserDetails myUserDetails = new MyUserDetails(user);

        doReturn(bookToReturn).when(bookService).updateBookById(bookToPut, 1L, roleSet);

        mockMvc.perform(put("/api/book/{id}", 1L)
                .with(user(myUserDetails))
                .content(asJsonString(bookToPut))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(bookToReturn)));

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}