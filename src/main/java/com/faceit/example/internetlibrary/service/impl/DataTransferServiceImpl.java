package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.mapper.elasticsearch.BookElasticsearchMapper;
import com.faceit.example.internetlibrary.service.DataTransferService;
import com.faceit.example.internetlibrary.service.elasticsearch.BookElasticsearchService;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataTransferServiceImpl implements DataTransferService {

    private static final int FIXED_DELAY = 20_000;
    private static int page = 0;

    private final BookMongoService bookMongoService;
    private final BookElasticsearchService elasticsearchService;
    private final BookElasticsearchMapper bookElasticsearchMapper;

    public DataTransferServiceImpl(BookMongoService bookMongoService,
                                   BookElasticsearchService elasticsearchService,
                                   BookElasticsearchMapper bookElasticsearchMapper) {
        this.bookMongoService = bookMongoService;
        this.elasticsearchService = elasticsearchService;
        this.bookElasticsearchMapper = bookElasticsearchMapper;
    }

    @Override
    @Async("threadPoolTaskExecutorTransfer")
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void mongoToElastic() {
        Pageable sortedById = PageRequest.of(page, 20, Sort.by("id"));
        List<com.faceit.example.internetlibrary.model.mongodb.Book> mongoBook =
                bookMongoService
                        .getAllBook(sortedById)
                        .getContent();
        List<com.faceit.example.internetlibrary.model.elasticsearch.Book> elasticBook =
                bookElasticsearchMapper
                        .mongoBooksToElasticBooks(mongoBook)
                        .stream()
                        .filter(book -> !elasticsearchService.existsByName(book.getName()))
                        .collect(Collectors.toList());
        if (!elasticBook.isEmpty()) {
            elasticsearchService.addBookBulk(elasticBook);
        }
        page++;
    }
}
