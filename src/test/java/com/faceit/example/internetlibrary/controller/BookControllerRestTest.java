package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.mapper.mysql.BookMapper;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.Role;
import com.faceit.example.internetlibrary.service.mysql.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
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

        doReturn(Lists.newArrayList(book1, book2)).when(bookService).getAllBook();

        mockMvc.perform(get("/api/book"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/book"))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[0].bookCondition", is("bookCondition1")))
                .andExpect(jsonPath("$[0].description", is("description1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].bookCondition", is("bookCondition2")))
                .andExpect(jsonPath("$[1].description", is("description2")));
    }



    @Test
    @DisplayName("GET /api/book/1 success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void testGetBookById() throws Exception {
        Book book = new Book(1L, "name1", "bookCondition1", "description1");
        String expectedResult =
                "{\"id\":1,\"name\":\"name1\",\"bookCondition\":\"bookCondition1\",\"description\":\"description1\"}";
        when(bookService.getBookById(1)).thenReturn(book);
        mockMvc.perform(get("/api/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    @DisplayName("POST /api/book success")
    @WithMockUser(username = "1", password = "1", roles = "EMPLOYEE")
    void testAddBook() throws Exception {
        String expectedResult = "{\"name\":\"name11\",\"bookCondition\":\"bookCondition11\",\"description\":\"description11\"}";
        Book bookToPost = new Book(1L, "name11", "bookCondition11", "description11");
        Book bookToReturn = new Book(1L, "name11", "bookCondition11", "description11");

        //Book book = new Book();
        //when(bookMapper.bookRequestToBook(new BookRequest())).thenReturn(book);
        //when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        //doNothing().when(userService).addNewUser(user);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(1,"ROLE_EMPLOYEE"));
        doReturn(bookToReturn).when(bookService).addBook(bookToPost, roleSet);
        //doNothing().when(bookService).addBook(bookToPost, roleSet);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("name11");
        bookRequest.setBookCondition("bookCondition11");
        bookRequest.setDescription("description11");
        mockMvc.perform(post("/api/book")
                .content(asJsonString(bookRequest))
                //.content(expectedResult)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void deleteBookById() {
    }

    @Test
    void updateBookById() {
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}