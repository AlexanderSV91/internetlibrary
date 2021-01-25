package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.BookMongo;

import java.util.List;

public interface BookMongoService {

    List<BookMongo> getAllBook();

    BookMongo getBookById(String id);

    BookMongo addBook(BookMongo newBook);

    void deleteBookById(String id);

    BookMongo findByName(String name);

    List<BookMongo> findByAuthors(String author);
}
