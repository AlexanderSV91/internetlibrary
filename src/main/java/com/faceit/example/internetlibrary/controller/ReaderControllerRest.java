package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.model.Reader;
import com.faceit.example.internetlibrary.sevice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class ReaderControllerRest {
    private final ReaderService readerService;

    @Autowired
    public ReaderControllerRest(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/reader")
    public List<Reader> getAllReader() {
        return readerService.getAllReader();
    }

    @GetMapping("/reader/{id}")
    public Reader getAllReaderById(@PathVariable long id) {
        return readerService.getReaderById(id);
    }

    @PostMapping("/reader")
    public Reader addReader(@RequestBody Reader newReader) {
        return readerService.addReader(newReader);
    }

    @DeleteMapping("/reader/{id}")
    public void deleteReaderById(@PathVariable long id) {
        readerService.deleteReaderById(id);
    }

    @PutMapping("/reader/{id}")
    public Reader updateReaderById(@RequestBody Reader updateReader, @PathVariable Long id) {
        return readerService.updateReaderById(updateReader, id);
    }
}
