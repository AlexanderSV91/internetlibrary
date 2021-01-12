package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.repository.BookRepository;
import com.faceit.example.internetlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        Book book = null;
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        return book;
    }

    @Override
    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBookById(Book updateBook, long id) {
        Book book = getBookById(id);
        if (book != null) {
            updateBook.setId(id);
        }
        bookRepository.save(updateBook);
        return updateBook;
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }
}