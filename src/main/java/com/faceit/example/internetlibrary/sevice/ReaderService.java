package com.faceit.example.internetlibrary.sevice;

import com.faceit.example.internetlibrary.model.Reader;

import java.util.List;

public interface ReaderService {
    List<Reader> getAllReader();

    Reader getReaderById(long id);

    Reader addReader(Reader newReader);
}
