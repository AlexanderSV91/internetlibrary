package com.faceit.example.internetlibrary.mapper.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookElasticsearchMapper {

    BookResponse bookMongoToBookMongoResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book bookMongoRequestToBookMongo(BookRequest bookRequest);

    List<BookResponse> booksToBooksResponse(List<Book> books);

    @Mapping(target = "id", ignore = true)
    Book updateBookFromBookRequest(BookRequest bookRequest, @MappingTarget Book book);
}
