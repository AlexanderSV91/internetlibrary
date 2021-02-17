package com.faceit.example.internetlibrary.service.impl.elasticsearch;

import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BooksResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.elasticsearch.BookElasticsearchMapper;
import com.faceit.example.internetlibrary.model.elasticsearch.ElasticBook;
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

import java.util.*;
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
    public Page<BookResponse> getAllBook(Pageable pageable) {
        Page<ElasticBook> books = bookElasticsearchRepository.findAll(pageable);
        if (books.getContent().isEmpty()) {
            throw new ResourceNotFoundException("exception.notFound");
        }
        List<BookResponse> bookResponseList = bookElasticsearchMapper.booksToBooksResponse(books.getContent());
        return pageEntityToPageResponse(books, bookResponseList);
    }

    @Override
    public BooksResponse aggregationFiledPrice(Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .addAggregation(new TermsAggregationBuilder("price")
                        .field("price")
                        .size(pageable.getPageSize()))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        Map<String, Long> aggregations = getAggregations(searchHits);
        List<BookResponse> bookResponses = bookElasticsearchMapper.booksToBooksResponse(books);
        return new BooksResponse(listBooksToPageBooksResponse(
                bookResponses, pageable, searchHits.getTotalHits()),
                aggregations);
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
                        .size(pageable.getPageSize()))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        Map<String, Long> aggregations = getAggregations(searchHits);
        List<BookResponse> bookResponses = bookElasticsearchMapper.booksToBooksResponse(books);
        return new BooksResponse(listBooksToPageBooksResponse(
                bookResponses, pageable, searchHits.getTotalHits()),
                aggregations);
    }

    @Override
    public Page<ElasticBook> searchBoolFieldsBookConditionAndPrice(int more, int less, String condition, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("bookCondition", condition))
                        .must(QueryBuilders.rangeQuery("price")
                                .gt(more)
                                .lt(less)))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchBoolFieldName(List<String> searchValue, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.termsQuery("name", searchValue)))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchRangeFieldPrice(int more, int less, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.rangeQuery("price")
                        .gt(more)
                        .lt(less))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public BooksResponse aggregateTerm(String field, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .addAggregation(new TermsAggregationBuilder(field)
                        .field(field)
                        .size(pageable.getPageSize()))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        Map<String, Long> aggregations = getAggregations(searchHits);
        List<BookResponse> bookResponses = bookElasticsearchMapper.booksToBooksResponse(books);
        return new BooksResponse(listBooksToPageBooksResponse(
                bookResponses, pageable, searchHits.getTotalHits()),
                aggregations);
    }

    @Override
    public Page<ElasticBook> searchMatchingFieldName(String text, Pageable pageable) {
        NativeSearchQuery searchQuery =
                new NativeSearchQueryBuilder()
                        .withPageable(pageable)
                        .withQuery(QueryBuilders.matchQuery("name", text)
                                .operator(Operator.AND))
                        .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchRegexFieldName(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withFilter(QueryBuilders.regexpQuery("name", text))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchNameWithFuzziness(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", text)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.ONE)
                        .prefixLength(3))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchQueryMultiMatchFieldsNameDescription(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.multiMatchQuery(text)
                        .field("name")
                        .field("description")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public Page<ElasticBook> searchPhraseFieldDescription(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(QueryBuilders.matchPhraseQuery("description", text).slop(1))
                .build();

        SearchHits<ElasticBook> searchHits = elasticsearch(searchQuery);
        List<ElasticBook> books = searchHitsToListBooks(searchHits);
        return listBooksToPageBooks(books, pageable, searchHits.getTotalHits());
    }

    @Override
    public ElasticBook getBookById(String id) {
        Optional<ElasticBook> optionalBook = bookElasticsearchRepository.findById(id);
        return Utils.getDataFromTypeOptional(optionalBook);
    }

    @Override
    public ElasticBook addBook(ElasticBook newBook) {
        return bookElasticsearchRepository.save(newBook);
    }

    @Override
    public void addBookBulk(List<ElasticBook> books) {
        bookElasticsearchRepository.saveAll(books);
    }

    @Override
    public void deleteBookById(String id) {
        bookElasticsearchRepository.deleteById(id);
    }

    @Override
    public ElasticBook updateBookById(BookRequest bookRequest, String id) {
        ElasticBook book = bookElasticsearchMapper.updateBookFromBookRequest(bookRequest, getBookById(id));
        return bookElasticsearchRepository.save(book);
    }

    @Override
    public ElasticBook findByName(String name) {
        return bookElasticsearchRepository.findByName(name);
    }

    @Override
    public List<ElasticBook> findByAuthors(String author) {
        return bookElasticsearchRepository.findByAuthors(author);
    }

    private Map<String, Long> getAggregations(SearchHits<ElasticBook> searchHits) {
        Objects.requireNonNull(searchHits.getAggregations());
        Map<String, Long> result = new HashMap<>();
        searchHits
                .getAggregations()
                .asList()
                .forEach(aggregation -> ((Terms) aggregation)
                        .getBuckets()
                        .forEach(bucket -> result.put(bucket.getKeyAsString(), bucket.getDocCount())));
        return result;
    }

    private PageImpl<BookResponse> pageEntityToPageResponse(Page<ElasticBook> page,
                                                            List<BookResponse> list) {
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

    private List<ElasticBook> searchHitsToListBooks(SearchHits<ElasticBook> searchHits) {
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private Page<ElasticBook> listBooksToPageBooks(List<ElasticBook> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }

    private Page<BookResponse> listBooksToPageBooksResponse(List<BookResponse> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }

    private SearchHits<ElasticBook> elasticsearch(NativeSearchQuery searchQuery) {
        return elasticsearchRestTemplate
                .search(searchQuery, ElasticBook.class, IndexCoordinates.of(ELASTICSEARCH_INDEX));
    }
}
