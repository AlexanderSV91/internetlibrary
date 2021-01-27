package com.faceit.example.internetlibrary.mapper.mongo;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse;
import com.faceit.example.internetlibrary.model.mongodb.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMongoMapper {

    BookResponse bookMongoToBookMongoResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book bookMongoRequestToBookMongo(BookRequest bookRequest);
}
