package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.faceit.example.internetlibrary.model.mongodb.Book;
import com.faceit.example.internetlibrary.service.ParsingSchedulerService;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ParsingSchedulerServiceImpl implements ParsingSchedulerService {

    @Value(value = "${parsingPage}")
    private String parsingPage;

    private final BookMongoService bookMongoService;

    @Autowired
    public ParsingSchedulerServiceImpl(BookMongoService bookMongoService) {
        this.bookMongoService = bookMongoService;
    }

    @Override
    @Scheduled(fixedDelay = 60_000)
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
            for (int i = 0; i < bookElements.size(); ) {
                Elements span3AppendBottom = bookElements.get(i).getAllElements();
                Elements span11Prepend1AppendBottom = bookElements.get(i + 1).getAllElements();
                Elements span3Prepend1AppendBottomLastCenter = bookElements.get(i + 2).getAllElements();

                Book book = new Book();
                book.setUrl("http://www.feedbooks.com" + span3AppendBottom.select("a").attr("href"));
                book.setImageUrl(span3AppendBottom.select("img").attr("src"));

                book.setName(span11Prepend1AppendBottom.select("h3").text());

                Elements bookAuthor = span11Prepend1AppendBottom
                        .select("h4")
                        .select("em")
                        .select("a");

                book.setAuthors(
                        bookAuthor
                                .stream()
                                .map(Element::text)
                                .collect(Collectors.toList()));

                book.setDescription(
                        span11Prepend1AppendBottom
                                .first()
                                .select("p")
                                .text());

                book.setBookCondition(
                        BookCondition
                                .values()[new Random().nextInt(BookCondition.values().length)]);

                book.setAddTime(LocalDateTime.now());

                book.setPrice(Double.parseDouble(
                        span3Prepend1AppendBottomLastCenter
                                .select("h3")
                                .first()
                                .text()
                                .replace("$ ", "")));

                if (!bookMongoService.existsByName(book.getName())) {
                    Book bm = bookMongoService.addBook(book);
                }

                parsingPage = "http://www.feedbooks.com" +
                        doc
                                .getElementsByClass("pagination")
                                .select("a")
                                .last()
                                .attr("href");
                i += 3;
            }
        }
    }
}
