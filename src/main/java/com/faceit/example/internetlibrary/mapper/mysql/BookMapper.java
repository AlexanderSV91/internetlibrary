package com.faceit.example.internetlibrary.mapper.mysql;

import com.faceit.example.internetlibrary.dto.request.mysql.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.model.mysql.Book;
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
}
