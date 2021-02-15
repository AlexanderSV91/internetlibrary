package com.faceit.example.internetlibrary.service.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BooksResponse;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BookElasticsearchService {

    BooksResponse aggregationFiledPrice(Pageable pageable);

    BooksResponse searchRangeFieldPriceAndAggregation(int more, int less, String field, Pageable pageable);

    Page<Book> searchBoolFieldsBookConditionAndPrice(int more, int less, String condition, Pageable pageable);

    Page<Book> searchBoolFieldName(String text1, String text2, Pageable pageable);

    Page<Book> searchRangeFieldPrice(int more, int less, Pageable pageable);

    BooksResponse aggregateTerm(String field, Pageable pageable);

    Page<Book> searchMatchingFieldName(String text, Pageable pageable);

    Page<Book> searchRegexFieldName(String text, Pageable pageable);

    Page<Book> searchNameWithFuzziness(String text, Pageable pageable);

    Page<Book> searchQueryMultiMatchFieldsNameDescription(String text, Pageable pageable);

    Page<Book> searchPhraseFieldDescription(String text, Pageable pageable);

    Page<Book> getAllBook(Pageable pageable);

    Book getBookById(String id);

    Book addBook(Book newBook);

    void addBookBulk(final List<Book> books);

    void deleteBookById(String id);

    Book updateBookById(BookRequest bookRequest, String id);

    Book findByName(String name);

    List<Book> findByAuthors(String author);

    boolean existsByName(String name);
}
