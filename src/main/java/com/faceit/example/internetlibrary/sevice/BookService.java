package com.faceit.example.internetlibrary.sevice;

import com.faceit.example.internetlibrary.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();

    Book getBookById(long id);
}
