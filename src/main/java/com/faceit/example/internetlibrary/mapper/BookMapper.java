package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.BookRequest;
import com.faceit.example.internetlibrary.dto.response.BookResponse;
import com.faceit.example.internetlibrary.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse bookToBookResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book bookRequestToBook(BookRequest bookRequest);

    List<BookResponse> booksToBooksResponse(List<Book> books);

    @Mapping(target = "id", ignore = true)
    Book updateBookFromBookRequest(BookRequest bookRequest, @MappingTarget Book book);

    com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse bookMongoToBookMongoResponse(com.faceit.example.internetlibrary.model.mongodb.Book book);

    com.faceit.example.internetlibrary.model.mongodb.Book bookMongoRequestToBookMongo(com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest bookRequest);
}
