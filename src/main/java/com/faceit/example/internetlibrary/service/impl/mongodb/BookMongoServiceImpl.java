package com.faceit.example.internetlibrary.service.impl.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.mapper.mongo.BookMongoMapper;
import com.faceit.example.internetlibrary.model.mongodb.Book;
import com.faceit.example.internetlibrary.repository.mongodb.BookMongoRepository;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMongoServiceImpl implements BookMongoService {

    private final BookMongoRepository bookMongoRepository;
    private final BookMongoMapper bookMongoMapper;

    @Autowired
    public BookMongoServiceImpl(BookMongoRepository bookMongoRepository,
                                BookMongoMapper bookMongoMapper) {
        this.bookMongoRepository = bookMongoRepository;
        this.bookMongoMapper = bookMongoMapper;
    }

    @Override
    public Page<Book> getAllBook(Pageable pageable) {
        return bookMongoRepository.findAll(pageable);
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
    public void addBookBulk(List<Book> books) {
        bookMongoRepository.saveAll(books);
    }

    @Override
    public void deleteBookById(String id) {
        bookMongoRepository.deleteById(id);
    }

    @Override
    public Book updateBookById(BookRequest bookRequest, String id) {
        Book updateBook = bookMongoMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
        return bookMongoRepository.save(updateBook);
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
