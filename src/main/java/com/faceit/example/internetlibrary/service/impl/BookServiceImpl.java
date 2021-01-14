package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.Role;
import com.faceit.example.internetlibrary.repository.BookRepository;
import com.faceit.example.internetlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Book addBook(Book newBook, Set<Role> roles) {
        boolean isEmployee = isEmployee(roles);
        if (isEmployee) {
            return bookRepository.save(newBook);
        } else {
            throw  new ApiRequestException("book not add");
        }
    }

    @Override
    public Book updateBookById(Book updateBook, long id, Set<Role> roles) {
        boolean isEmployee = isEmployee(roles);
        if (isEmployee) {
            Book book = getBookById(id);
            if (book != null) {
                updateBook.setId(id);
            }
            bookRepository.save(updateBook);
        } else {
            throw new ApiRequestException("book not updated");
        }
        return updateBook;
    }

    @Override
    public void deleteBookById(long id, Set<Role> roles) {
        boolean isEmployee = isEmployee(roles);
        if (isEmployee) {
            bookRepository.deleteById(id);
        } else {
            throw new ApiRequestException("book not delete");
        }
    }

    private boolean isEmployee(Set<Role> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals("ROLE_EMPLOYEE"));
    }
}
