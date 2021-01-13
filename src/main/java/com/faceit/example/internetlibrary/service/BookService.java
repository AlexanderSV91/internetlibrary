package com.faceit.example.internetlibrary.service;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.Role;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getAllBook();

    Book getBookById(long id);

    Book addBook(Book newBook, Set<Role> roleSet);

    Book updateBookById(Book updateBook, long id, Set<Role> roleSet);

    void deleteBookById(long id, Set<Role> roleSet);
}
