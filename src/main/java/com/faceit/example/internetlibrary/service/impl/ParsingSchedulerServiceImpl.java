package com.faceit.example.internetlibrary.service.impl;

import com.faceit.example.internetlibrary.model.BookMongo;
import com.faceit.example.internetlibrary.service.BookMongoService;
import com.faceit.example.internetlibrary.service.ParsingSchedulerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ParsingSchedulerServiceImpl implements ParsingSchedulerService {

    private static final String CRON = "0 0 */1 * * *";
    private static final int FIRST_PAGE = 1;
    private static int CURRENT_PAGE = 1;
    private static int LAST_PAGE;

    @Value(value = "${parsingPage}")
    private String parsingPage;

    /*private final BookMongoService bookMongoService;

    public ParsingSchedulerServiceImpl(BookMongoService bookMongoService) {
        this.bookMongoService = bookMongoService;
    }*/

    @Override
    //@Scheduled(cron = CRON)
    @Scheduled(fixedDelay = 10_000)
    public void parsingPage() {
        parsing();
    }

    private void parsing() {
        Document doc = null;
        try {
            doc = Jsoup.connect(parsingPage + CURRENT_PAGE)
                    .userAgent("Mozilla")
                    //.timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements bookElements = doc.getElementsByClass("append-bottom");
        String s = "http://www.feedbooks.com" + doc.getElementsByClass("pagination").select("a").last().attr("href");
        parsingPage = s;
        System.out.println(s);

//        for (int i = 0; i < bookElements.size(); ) {
//            Elements span3AppendBottom = bookElements.get(i).getAllElements();
//            Elements span11Prepend1AppendBottom = bookElements.get(i + 1).getAllElements();
//            Elements span3Prepend1AppendBottomLastCenter = bookElements.get(i + 2).getAllElements();
//
//            BookMongo bookMongo = new BookMongo();
//            bookMongo.setUrl("http://www.feedbooks.com" + span3AppendBottom.select("a").attr("href"));
//            bookMongo.setImageUrl(span3AppendBottom.select("img").attr("src"));
//
//            bookMongo.setName(span11Prepend1AppendBottom.select("h3").text());
//            Elements bookAuthor = span11Prepend1AppendBottom.select("h4").select("em").select("a");
//            bookMongo.setAuthors(bookAuthor.stream().map(Element::text).collect(Collectors.toList()));
//            bookMongo.setDescription(span11Prepend1AppendBottom.first().select("p").text());
//            bookMongo.setAddTime(LocalDateTime.now());
//
//            bookMongo.setPrice(Double.parseDouble(span3Prepend1AppendBottomLastCenter.select("h3").first().text().replace("$ ", "")));
//            System.out.println(bookMongo);
//
//            //bookMongoService.addBook(bookMongo);
//
//
//            CURRENT_PAGE++;
//            i += 3;
//        }
    }
}
