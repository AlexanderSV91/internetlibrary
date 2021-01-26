package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.BookMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<BookMongo, String> {

    BookMongo findByName(String name);

    List<BookMongo> findByAuthors(String author);
}
