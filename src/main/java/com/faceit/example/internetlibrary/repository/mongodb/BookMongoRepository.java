package com.faceit.example.internetlibrary.repository.mongodb;

import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<MongoBook, String> {

    MongoBook findByName(String name);

    List<MongoBook> findByAuthors(String author);

    boolean existsByName(String name);
}
