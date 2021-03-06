package com.faceit.example.internetlibrary.service.impl.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.exception.ApiRequestException;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.mysql.BookMapper;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.Role;
import com.faceit.example.internetlibrary.repository.mysql.BookRepository;
import com.faceit.example.internetlibrary.service.mysql.BookService;
import com.faceit.example.internetlibrary.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public Page<BookResponse> getPagingBook(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        if (books.getContent().isEmpty()) {
            throw new ResourceNotFoundException("exception.notFound");
        }
        List<BookResponse> bookResponseList = bookMapper.booksToBooksResponse(books.getContent());
        return pageEntityToPageResponse(books, bookResponseList);
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable(value = "book", key = "#id")
    public Book getBookById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBook);
    }

    @Override
    @CachePut(value = "book", key = "#newBook.id")
    public Book addBook(Book newBook, Set<Role> roles) {
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            return bookRepository.save(newBook);
        } else {
            throw new ApiRequestException("exception.bookNotAdd");
        }
    }

    @Override
    @CachePut(value = "book", key = "#id")
    public Book updateBookById(BookRequest bookRequest, long id, Set<Role> roles) {
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            Book updateBook = bookMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
            return bookRepository.save(updateBook);
        } else {
            throw new ResourceNotFoundException("exception.notFound");
        }
    }

    @Override
    @CacheEvict(value = "book", key = "#id")
    public void deleteBookById(long id, Set<Role> roles) {
        boolean isEmployee = Utils.isEmployee(roles);
        if (isEmployee) {
            bookRepository.deleteById(id);
        } else {
            throw new ApiRequestException("exception.bookNotDelete");
        }
    }

    private PageImpl<BookResponse> pageEntityToPageResponse(Page<Book> page, List<BookResponse> list) {
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
