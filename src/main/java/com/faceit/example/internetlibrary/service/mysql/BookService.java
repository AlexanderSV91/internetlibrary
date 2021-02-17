package com.faceit.example.internetlibrary.service.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.model.mysql.Book;
import com.faceit.example.internetlibrary.model.mysql.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface BookService {

    Page<BookResponse> getPagingBook(Pageable pageable);

    List<Book> getAllBook();

    Book getBookById(long id);

    Book addBook(Book newBook, Set<Role> roleSet);

    Book updateBookById(BookRequest bookRequest, long id, Set<Role> roleSet);

    void deleteBookById(long id, Set<Role> roleSet);
}
