package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
