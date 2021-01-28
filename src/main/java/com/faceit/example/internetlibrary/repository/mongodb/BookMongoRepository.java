package com.faceit.example.internetlibrary.repository.mongodb;

import com.faceit.example.internetlibrary.model.mongodb.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<Book, String> {

    Book findByName(String name);

    List<Book> findByAuthors(String author);

    boolean existsByName(String name);
}
