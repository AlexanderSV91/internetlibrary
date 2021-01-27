package com.faceit.example.internetlibrary.service.impl.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.mapper.mongo.BookMongoMapper;
import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.faceit.example.internetlibrary.model.mongodb.Book;
import com.faceit.example.internetlibrary.repository.mongodb.BookMongoRepository;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookMongoServiceImpl implements BookMongoService {

    private final BookMongoRepository bookMongoRepository;
    private final BookMongoMapper bookMongoMapper;

    public BookMongoServiceImpl(BookMongoRepository bookMongoRepository,
                                BookMongoMapper bookMongoMapper) {
        this.bookMongoRepository = bookMongoRepository;
        this.bookMongoMapper = bookMongoMapper;
    }

    @Override
    public List<Book> test() {
        List<Book> books = getAllBook();

/*        List<Book> test = books
                .stream()
                .filter(book -> BookCondition.GOOD.equals(book.getBookCondition()))
                .collect(Collectors.toList());*/

        List<Book> test = new ArrayList<>();
        long count = books
                .stream()
                .filter(book -> BookCondition.GOOD.equals(book.getBookCondition()))
                .count();
        System.out.println(count);

        return test;
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
