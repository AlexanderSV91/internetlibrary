package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();

    Book getBookById(long id);

    Book addBook(Book newBook);

    Book updateBookById(Book updateBook, long id);

    void deleteBookById(long id);
}
