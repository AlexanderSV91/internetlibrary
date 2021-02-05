package com.faceit.example.internetlibrary.repository.elasticsearch;


import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookElasticsearchRepository extends ElasticsearchRepository<Book, String> {

    Book findByName(String name);

    List<Book> findByAuthors(String author);

    boolean existsByName(String name);
}
