package com.faceit.example.internetlibrary.mapper.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.model.elasticsearch.ElasticBook;
import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookElasticsearchMapper {

    BookResponse bookElasticToBookElasticResponse(ElasticBook book);

    @Mapping(target = "id", ignore = true)
    ElasticBook bookElasticRequestToBookElastic(BookRequest bookRequest);

    List<BookResponse> booksToBooksResponse(List<ElasticBook> books);

    @Mapping(target = "id", ignore = true)
    ElasticBook updateBookFromBookRequest(BookRequest bookRequest, @MappingTarget ElasticBook book);

    List<ElasticBook> mongoBooksToElasticBooks(List<MongoBook> mongoBook);
}
