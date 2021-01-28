package com.faceit.example.internetlibrary.service.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.model.mongodb.Book;

import java.util.List;

public interface BookMongoService {

    List<Book> getAllBook();

    Book getBookById(String id);

    Book addBook(Book newBook);

    void deleteBookById(String id);

    Book updateBookById(BookRequest bookRequest, String id);

    Book findByName(String name);

    List<Book> findByAuthors(String author);

    boolean existsByName(String name);
}
