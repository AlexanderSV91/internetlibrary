package com.faceit.example.internetlibrary.service.impl.mongodb;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.mongo.BookMongoMapper;
import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import com.faceit.example.internetlibrary.repository.mongodb.BookMongoRepository;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<BookResponse> getAllBookResponse(Pageable pageable) {
        Page<MongoBook> books = bookMongoRepository.findAll(pageable);
        if (books.getContent().isEmpty()) {
            throw new ResourceNotFoundException("exception.notFound");
        }
        List<BookResponse> bookResponseList = bookMongoMapper.booksToBooksResponse(books.getContent());
        return pageEntityToPageResponse(books, bookResponseList);
    }

    @Override
    public Page<MongoBook> getAllMongoBook(Pageable pageable) {
        return bookMongoRepository.findAll(pageable);
    }

    @Override
    public MongoBook getBookById(String id) {
        Optional<MongoBook> optionalBookMongo = bookMongoRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBookMongo);
    }

    @Override
    public MongoBook addBook(MongoBook newBook) {
        return bookMongoRepository.save(newBook);
    }

    @Override
    public void addBookBulk(List<MongoBook> books) {
        bookMongoRepository.saveAll(books);
    }

    @Override
    public void deleteBookById(String id) {
        bookMongoRepository.deleteById(id);
    }

    @Override
    public MongoBook updateBookById(BookRequest bookRequest, String id) {
        MongoBook updateBook = bookMongoMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
        return bookMongoRepository.save(updateBook);
    }

    @Override
    public MongoBook findByName(String name) {
        return bookMongoRepository.findByName(name);
    }

    @Override
    public List<MongoBook> findByAuthors(String author) {
        return bookMongoRepository.findByAuthors(author);
    }

    @Override
    public boolean existsByName(String name) {
        return bookMongoRepository.existsByName(name);
    }

    private PageImpl<BookResponse> pageEntityToPageResponse(Page<MongoBook> page,
                                                            List<BookResponse> list) {
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
