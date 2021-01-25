package com.faceit.example.internetlibrary.mapper;

import com.faceit.example.internetlibrary.dto.request.BookRequest;
import com.faceit.example.internetlibrary.dto.response.BookResponse;
import com.faceit.example.internetlibrary.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse bookToBookResponse(Book book);

    Book bookRequestToBook(BookRequest bookRequest);
}
