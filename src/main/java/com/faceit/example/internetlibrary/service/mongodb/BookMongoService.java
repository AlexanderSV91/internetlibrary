package com.faceit.example.internetlibrary.service.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.model.mongodb.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookMongoService {

    Page<Book> getAllBook(Pageable pageable);

    Book getBookById(String id);

    Book addBook(Book newBook);

    void addBookBulk(List<Book> books);

    void deleteBookById(String id);

    Book updateBookById(BookRequest bookRequest, String id);

    Book findByName(String name);

    List<Book> findByAuthors(String author);

    boolean existsByName(String name);
}
