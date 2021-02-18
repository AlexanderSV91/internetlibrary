package com.faceit.example.internetlibrary.service.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BooksResponse;
import com.faceit.example.internetlibrary.model.elasticsearch.ElasticBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookElasticsearchService {

    BooksResponse aggregationFiledPrice(Pageable pageable);

    BooksResponse searchRangeFieldPriceAndAggregation(int more, int less, String field, Pageable pageable);

    Page<ElasticBook> searchBoolFieldsBookConditionAndPrice(int more, int less, String condition, Pageable pageable);

    Page<ElasticBook> searchBoolFieldName(List<String> searchValue, Pageable pageable);

    Page<ElasticBook> searchRangeFieldPrice(int more, int less, Pageable pageable);

    BooksResponse aggregateTerm(String field, Pageable pageable);

    Page<ElasticBook> searchMatchingFieldName(String text, Pageable pageable);

    Page<ElasticBook> searchRegexFieldName(String text, Pageable pageable);

    Page<ElasticBook> searchNameWithFuzziness(String text, Pageable pageable);

    Page<ElasticBook> searchQueryMultiMatchFieldsNameDescription(String text, Pageable pageable);

    Page<ElasticBook> searchPhraseFieldDescription(String text, Pageable pageable);

    Page<BookResponse> getAllBook(Pageable pageable);

    ElasticBook getBookById(String id);

    ElasticBook addBook(ElasticBook newBook);

    void addBookBulk(final List<ElasticBook> books);

    void deleteBookById(String id);

    ElasticBook updateBookById(BookRequest bookRequest, String id);

    ElasticBook findByName(String name);

    List<ElasticBook> findByAuthors(String author);
}
