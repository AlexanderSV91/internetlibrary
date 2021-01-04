package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
