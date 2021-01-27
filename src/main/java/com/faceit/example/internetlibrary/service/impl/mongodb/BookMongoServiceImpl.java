package com.faceit.example.internetlibrary.service.impl.mongodb;

import com.faceit.example.internetlibrary.model.mongodb.Book;
import com.faceit.example.internetlibrary.repository.mongodb.BookMongoRepository;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMongoServiceImpl implements BookMongoService {

    private final BookMongoRepository bookMongoRepository;

    public BookMongoServiceImpl(BookMongoRepository bookMongoRepository) {
        this.bookMongoRepository = bookMongoRepository;
    }

    @Override
    public List<Book> getAllBook() {
        return bookMongoRepository.findAll();
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> optionalBookMongo = bookMongoRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBookMongo);
    }

    @Override
    public Book addBook(Book newBook) {
        return bookMongoRepository.save(newBook);
    }

    @Override
    public void deleteBookById(String id) {
        bookMongoRepository.deleteById(id);
    }

    @Override
    public Book findByName(String name) {
        return bookMongoRepository.findByName(name);
    }

    @Override
    public List<Book> findByAuthors(String author) {
        return bookMongoRepository.findByAuthors(author);
    }

    @Override
    public boolean existsByName(String name) {
        return bookMongoRepository.existsByName(name);
    }
}
