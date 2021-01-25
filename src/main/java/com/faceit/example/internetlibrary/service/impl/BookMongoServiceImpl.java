package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.BookMongo;
import com.faceit.example.internetlibrary.repository.BookMongoRepository;
import com.faceit.example.internetlibrary.service.BookMongoService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*@Service
public class BookMongoServiceImpl implements BookMongoService {

    private final BookMongoRepository bookMongoRepository;

    public BookMongoServiceImpl(BookMongoRepository bookMongoRepository) {
        this.bookMongoRepository = bookMongoRepository;
    }

    @Override
    public List<BookMongo> getAllBook() {
        return bookMongoRepository.findAll();
    }

    @Override
    public BookMongo getBookById(String id) {
        Optional<BookMongo> optionalBookMongo = bookMongoRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBookMongo);
    }

    @Override
    public BookMongo addBook(BookMongo newBook) {
        return bookMongoRepository.save(newBook);
    }

    @Override
    public void deleteBookById(String id) {
        bookMongoRepository.deleteById(id);
    }

    @Override
    public BookMongo findByName(String name) {
        return bookMongoRepository.findByName(name);
    }

    @Override
    public List<BookMongo> findByAuthors(String author) {
        return bookMongoRepository.findByAuthors(author);
    }
}*/
