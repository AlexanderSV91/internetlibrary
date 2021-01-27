package com.faceit.example.internetlibrary.repository.mysql;

import com.faceit.example.internetlibrary.model.mysql.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
