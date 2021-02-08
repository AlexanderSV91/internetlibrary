package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.faceit.example.internetlibrary.service.ParsingSchedulerService;
import com.faceit.example.internetlibrary.service.elasticsearch.BookElasticsearchService;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ParsingSchedulerServiceImpl implements ParsingSchedulerService {

    private static final int FIXED_DELAY = 60_000;

    @Value(value = "${parsingPage}")
    private String parsingPage;

    private final BookMongoService bookMongoService;
    private final BookElasticsearchService bookElasticsearchService;

    @Autowired
    public ParsingSchedulerServiceImpl(BookMongoService bookMongoService,
                                       BookElasticsearchService bookElasticsearchService) {
        this.bookMongoService = bookMongoService;
        this.bookElasticsearchService = bookElasticsearchService;
    }

    @Override
    @Async("threadPoolTaskExecutorParsing")
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void parsingPage() {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(parsingPage)
                    .userAgent("Mozilla")
                    .timeout(15_000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements bookElements = null;
        if (doc != null) {
            bookElements = doc.getElementsByClass("append-bottom");
        }

        if (bookElements != null) {
            int size = bookElements.size();
            List<com.faceit.example.internetlibrary.model.mongodb.Book> mongoBooks = new ArrayList<>(size);
            List<com.faceit.example.internetlibrary.model.elasticsearch.Book> elasticBooks = new ArrayList<>(size);

            for (int i = 0; i < size; ) {
                Elements span3AppendBottom = bookElements.get(i).getAllElements();
                Elements span11Prepend1AppendBottom = bookElements.get(i + 1).getAllElements();
                Elements span3Prepend1AppendBottomLastCenter = bookElements.get(i + 2).getAllElements();

                String url = "http://www.feedbooks.com" + span3AppendBottom.select("a").attr("href");
                String imageUrl = span3AppendBottom.select("img").attr("src");
                String name = span11Prepend1AppendBottom.select("h3").text();

                Elements bookAuthor = span11Prepend1AppendBottom
                        .select("h4")
                        .select("em")
                        .select("a");

                List<String> authors = bookAuthor
                        .stream()
                        .map(Element::text)
                        .collect(Collectors.toList());

                String description = span11Prepend1AppendBottom
                        .first()
                        .select("p")
                        .text();

                BookCondition bookCondition = BookCondition
                        .values()[new Random()
                        .nextInt(BookCondition.values().length)];

                double price = Double.parseDouble(
                        span3Prepend1AppendBottomLastCenter
                                .select("h3")
                                .first()
                                .text()
                                .replace("$ ", ""));

                LocalDateTime addTime = LocalDateTime.now();

                if (!bookMongoService.existsByName(name)) {
                    com.faceit.example.internetlibrary.model.mongodb.Book book =
                            new com.faceit.example.internetlibrary.model.mongodb.Book();
                    book.setUrl(url);
                    book.setImageUrl(imageUrl);
                    book.setName(name);
                    book.setAuthors(authors);
                    book.setDescription(description);
                    book.setBookCondition(bookCondition);
                    book.setPrice(price);
                    book.setAddTime(addTime);

                    mongoBooks.add(book);
                }

                if (!bookElasticsearchService.existsByName(name)) {
                    com.faceit.example.internetlibrary.model.elasticsearch.Book book =
                            new com.faceit.example.internetlibrary.model.elasticsearch.Book();
                    book.setName(name);
                    book.setAuthors(authors);
                    book.setDescription(description);
                    book.setBookCondition(bookCondition);
                    book.setPrice(price);
                    book.setAddTime(addTime);

                    elasticBooks.add(book);
                }

                parsingPage = "http://www.feedbooks.com" +
                        doc
                                .getElementsByClass("pagination")
                                .select("a")
                                .last()
                                .attr("href");
                i += 3;
            }
            bookMongoService.addBookBulk(mongoBooks);
            bookElasticsearchService.addBookBulk(elasticBooks);
        }
    }
}
