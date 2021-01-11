package com.faceit.example.internetlibrary.sevice.impl;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.repository.ReaderRepository;
import com.faceit.example.internetlibrary.sevice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public List<Reader> getAllReader() {
        return readerRepository.findAll();
    }

    @Override
    public Reader getReaderById(long id) {
        Reader reader = null;
        Optional<Reader> optionalReader = readerRepository.findById(id);
        if (optionalReader.isPresent()) {
            reader = optionalReader.get();
        }
        return reader;
    }

    @Override
    public Reader addReader(Reader newReader) {
        return readerRepository.save(newReader);
    }

    @Override
    public Reader updateReaderById(Reader updateReader, long id) {
        Reader reader = getReaderById(id);
        if (reader != null) {
            updateReader.setId(id);
        }
        readerRepository.save(updateReader);
        return updateReader;
    }

    @Override
    public void deleteReaderById(long id) {
        readerRepository.deleteById(id);
    }
}
