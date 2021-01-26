package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.BookRequest;
import com.faceit.example.internetlibrary.dto.response.BookResponse;
import com.faceit.example.internetlibrary.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse bookToBookResponse(Book book);

    Book bookRequestToBook(BookRequest bookRequest);

    List<BookResponse> booksToBooksResponse(List<Book> books);

    com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse bookMongoToBookMongoResponse(com.faceit.example.internetlibrary.model.mongodb.Book book);

    com.faceit.example.internetlibrary.model.mongodb.Book bookMongoRequestToBookMongo(com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest bookRequest);
}
