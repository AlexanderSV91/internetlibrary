package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.models.Books;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Books, Long> {
}
