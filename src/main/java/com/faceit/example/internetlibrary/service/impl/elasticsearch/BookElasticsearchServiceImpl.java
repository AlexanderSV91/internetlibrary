package com.faceit.example.internetlibrary.service.impl.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BooksResponse;
import com.faceit.example.internetlibrary.mapper.elasticsearch.BookElasticsearchMapper;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import com.faceit.example.internetlibrary.repository.elasticsearch.BookElasticsearchRepository;
import com.faceit.example.internetlibrary.service.elasticsearch.BookElasticsearchService;
import com.faceit.example.internetlibrary.util.Utils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookElasticsearchServiceImpl implements BookElasticsearchService {

    private static final String ELASTICSEARCH_INDEX = "books";

    private final BookElasticsearchRepository bookElasticsearchRepository;
    private final BookElasticsearchMapper bookElasticsearchMapper;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public BookElasticsearchServiceImpl(BookElasticsearchRepository bookElasticsearchRepository,
                                        BookElasticsearchMapper bookElasticsearchMapper,
                                        ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.bookElasticsearchRepository = bookElasticsearchRepository;
        this.bookElasticsearchMapper = bookElasticsearchMapper;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public Page<Book> getAllBook(Pageable pageable) {
        //return searchMatchingFieldName("First Flush", pageable);
        //return searchRegex(".*first.*", pageable);
        //return searchNameWithFuzziness("firs", pageable);
        //return searchQueryMultiMatchFieldsNameDescription("first", pageable);
        //return searchPhraseFieldDescription("poet", pageable);
        //aggregateTerm("bookCondition", pageable).forEach((s, aLong) -> System.out.println(s + " : " + aLong));
        //return searchRangeFieldPrice(5, 10, pageable);
        //return searchBoolFieldName("first", "flush", pageable);
        //return searchBoolFieldsBookConditionAndPrice(1, 5, BookCondition.NEW.name(), pageable);


        return bookElasticsearchRepository.findAll(pageable);
    }

    @Override
    public BooksResponse searchRangeFieldPriceAndAggregation(int more, int less, String field, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.rangeQuery("price")
                        .gt(more)
                        .lt(less))
                .addAggregation(new TermsAggregationBuilder(field)
                        .field(field)
                        .size(20))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        Map<String, Long> resultAggr = new HashMap<>();
        searchHits
                .getAggregations()
                .asList()
                .forEach(aggregation -> {
                    ((Terms) aggregation)
                            .getBuckets()
                            .forEach(bucket -> resultAggr.put(bucket.getKeyAsString(), bucket.getDocCount()));
                });

        List<BookResponse> bookResponses = bookElasticsearchMapper.booksToBooksResponse(books);
        return new BooksResponse(listBooksToPageBooksResponse(bookResponses, pageable, searchHits.getTotalHits()), resultAggr);
    }

    @Override
    public Page<Book> searchBoolFieldsBookConditionAndPrice(int more, int less, String condition, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("bookCondition", condition))
                        .must(QueryBuilders.rangeQuery("price")
                                .gt(more)
                                .lt(less)))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchBoolFieldName(String text1, String text2, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.termQuery("name", text1))
                        .should(QueryBuilders.termQuery("name", text2)))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchRangeFieldPrice(int more, int less, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.rangeQuery("price")
                        .gt(more)
                        .lt(less))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public BooksResponse aggregateTerm(String field, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .addAggregation(new TermsAggregationBuilder(field)
                        .field(field)
                        .size(20))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        Map<String, Long> resultAggr = new HashMap<>();
        searchHits
                .getAggregations()
                .asList()
                .forEach(aggregation -> {
                    ((Terms) aggregation)
                            .getBuckets()
                            .forEach(bucket -> resultAggr.put(bucket.getKeyAsString(), bucket.getDocCount()));
                });
        List<BookResponse> bookResponses = bookElasticsearchMapper.booksToBooksResponse(books);
        return new BooksResponse(listBooksToPageBooksResponse(bookResponses, pageable, searchHits.getTotalHits()), resultAggr);
    }

    @Override
    public Page<Book> searchMatchingFieldName(String text, Pageable pageable) {
        NativeSearchQuery searchQuery =
                new NativeSearchQueryBuilder()
                        .withPageable(pageable)
                        .withQuery(QueryBuilders.matchQuery("name", text)
                                .operator(Operator.AND))
                        .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchRegexFieldName(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withFilter(QueryBuilders.regexpQuery("name", text))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchNameWithFuzziness(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", text)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.ONE)
                        .prefixLength(3))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchQueryMultiMatchFieldsNameDescription(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.multiMatchQuery(text)
                        .field("name")
                        .field("description")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<Book> searchPhraseFieldDescription(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.matchPhraseQuery("description", text).slop(1))
                .build();

        SearchHits<Book> searchHits = elasticsearch(searchQuery);
        List<Book> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> optionalBook = bookElasticsearchRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBook);
    }

    @Override
    public Book addBook(Book newBook) {
        return bookElasticsearchRepository.save(newBook);
    }

    @Override
    public void addBookBulk(List<Book> books) {
        bookElasticsearchRepository.saveAll(books);
    }

    @Override
    public void deleteBookById(String id) {
        bookElasticsearchRepository.deleteById(id);
    }

    @Override
    public Book updateBookById(BookRequest bookRequest, String id) {
        Book book = bookElasticsearchMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
        return bookElasticsearchRepository.save(book);
    }

    @Override
    public Book findByName(String name) {
        return bookElasticsearchRepository.findByName(name);
    }

    @Override
    public List<Book> findByAuthors(String author) {
        return bookElasticsearchRepository.findByAuthors(author);
    }

    @Override
    public boolean existsByName(String name) {
        Book book = bookElasticsearchRepository.existsByName(name);
        return null != book;
    }

    private List<Book> searchHitsToListBooks(SearchHits<Book> searchHits) {
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private Page<Book> listBooksToPageBooks(List<Book> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }

    private Page<BookResponse> listBooksToPageBooksResponse(List<BookResponse> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }

    private SearchHits<Book> elasticsearch(NativeSearchQuery searchQuery) {
        return elasticsearchRestTemplate
                .search(searchQuery, Book.class, IndexCoordinates.of(ELASTICSEARCH_INDEX));
    }
}
