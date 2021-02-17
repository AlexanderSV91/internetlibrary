package com.faceit.example.internetlibrary.repository.elasticsearch;


import com.faceit.example.internetlibrary.model.elasticsearch.ElasticBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookElasticsearchRepository extends ElasticsearchRepository<ElasticBook, String> {

    ElasticBook findByName(String name);

    List<ElasticBook> findByAuthors(String author);
}
