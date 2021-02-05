package com.faceit.example.internetlibrary.service.impl.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.mapper.elasticsearch.BookElasticsearchMapper;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import com.faceit.example.internetlibrary.repository.elasticsearch.BookElasticsearchRepository;
import com.faceit.example.internetlibrary.service.elasticsearch.BookElasticsearchService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookElasticsearchServiceImpl implements BookElasticsearchService {

    private final BookElasticsearchRepository bookElasticsearchRepository;
    private final BookElasticsearchMapper bookElasticsearchMapper;

    @Autowired
    public BookElasticsearchServiceImpl(BookElasticsearchRepository bookElasticsearchRepository,
                                        BookElasticsearchMapper bookElasticsearchMapper) {
        this.bookElasticsearchRepository = bookElasticsearchRepository;
        this.bookElasticsearchMapper = bookElasticsearchMapper;
    }

    @Override
    public Page<Book> getAllBook(Pageable pageable) {
        return bookElasticsearchRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> optionalBook = bookElasticsearchRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBook);
    }

    @Override
    public Book addBook(Book newBook) {
        return bookElasticsearchRepository.save(newBook);
    }

    @Override
    public void addBookBulk(List<Book> books) {
        bookElasticsearchRepository.saveAll(books);
    }

    @Override
    public void deleteBookById(String id) {
        bookElasticsearchRepository.deleteById(id);
    }

    @Override
    public Book updateBookById(BookRequest bookRequest, String id) {
        Book book = bookElasticsearchMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
        return bookElasticsearchRepository.save(book);
    }

    @Override
    public Book findByName(String name) {
        return bookElasticsearchRepository.findByName(name);
    }

    @Override
    public List<Book> findByAuthors(String author) {
        return bookElasticsearchRepository.findByAuthors(author);
    }

    @Override
    public boolean existsByName(String name) {
        return bookElasticsearchRepository.existsByName(name);
    }
}
