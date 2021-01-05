package com.faceit.example.internetlibrary.repository;

import com.faceit.example.internetlibrary.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
