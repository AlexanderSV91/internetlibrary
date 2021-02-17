package com.faceit.example.internetlibrary.mapper.mongo;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse;
import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMongoMapper {

    BookResponse bookMongoToBookMongoResponse(MongoBook book);

    @Mapping(target = "id", ignore = true)
    MongoBook bookMongoRequestToBookMongo(BookRequest bookRequest);

    List<BookResponse> booksToBooksResponse(List<MongoBook> books);

    @Mapping(target = "id", ignore = true)
    MongoBook updateBookFromBookRequest(BookRequest bookRequest, @MappingTarget MongoBook book);
}
