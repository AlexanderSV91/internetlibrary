package com.faceit.example.internetlibrary.util;

import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.exception.ApiException;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import com.faceit.example.internetlibrary.model.mysql.Role;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(Set<Role> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ROLE_EMPLOYEE));
    }

    public static ApiException buildApiException(String simpleName,
                                                 Map<String, String> message,
                                                 HttpStatus badRequest,
                                                 LocalDateTime now) {
        return new ApiException(simpleName, message, badRequest, now);
    }

    public static Map<String, String> buildMap(String key, String value) {
        return new HashMap<>() {{
            put(key, value);
        }};
    }

    public static <T> T getDataFromTypeOptional(Optional<T> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException("exception.notFound"));
    }

    public static String getMessageForLocale(String messageKey) {
        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        return ResourceBundle.getBundle("messages", locale).getString(messageKey);
    }

    public static <R, T> PageImpl<R> pageEntityToPageResponse(Page<T> page, List<R> list) {
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

    public static Map<String, Long> getAggregations(SearchHits<Book> searchHits) {
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

    public static List<Book> searchHitsToListBooks(SearchHits<Book> searchHits) {
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public static Page<Book> listBooksToPageBooks(List<Book> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }

    public static Page<BookResponse> listBooksToPageBooksResponse(List<BookResponse> books, Pageable pageable, long total) {
        return new PageImpl<>(books, pageable, total);
    }
}
