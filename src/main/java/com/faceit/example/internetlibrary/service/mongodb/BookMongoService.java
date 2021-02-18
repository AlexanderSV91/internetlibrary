package com.faceit.example.internetlibrary.service.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse;
import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookMongoService {

    Page<BookResponse> getAllBookResponse(Pageable pageable);

    Page<MongoBook> getAllMongoBook(Pageable pageable);

    MongoBook getBookById(String id);

    MongoBook addBook(MongoBook newBook);

    void addBookBulk(List<MongoBook> books);

    void deleteBookById(String id);

    MongoBook updateBookById(BookRequest bookRequest, String id);

    MongoBook findByName(String name);

    List<MongoBook> findByAuthors(String author);

    boolean existsByName(String name);
}
