package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.models.Readers;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Readers, Long> {
}
