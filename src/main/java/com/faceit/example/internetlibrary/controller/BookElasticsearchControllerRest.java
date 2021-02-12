package com.faceit.example.internetlibrary.controller;


import com.faceit.example.internetlibrary.dto.request.elasticsearch.BookRequest;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BookResponse;
import com.faceit.example.internetlibrary.dto.response.elasticsearch.BooksResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.elasticsearch.BookElasticsearchMapper;
import com.faceit.example.internetlibrary.model.elasticsearch.Book;
import com.faceit.example.internetlibrary.service.elasticsearch.BookElasticsearchService;
import com.faceit.example.internetlibrary.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "BookElasticsearch", description = "interaction with books in elasticsearch")
@SecurityRequirement(name = "basic")
public class BookElasticsearchControllerRest {

    private final BookElasticsearchService bookElasticsearchService;
    private final BookElasticsearchMapper bookElasticsearchMapper;

    public BookElasticsearchControllerRest(BookElasticsearchService bookElasticsearchService,
                                           BookElasticsearchMapper bookElasticsearchMapper) {
        this.bookElasticsearchService = bookElasticsearchService;
        this.bookElasticsearchMapper = bookElasticsearchMapper;
    }

    @GetMapping("/elasticsearch-book")
    @Operation(summary = "get all books from elasticsearch",
            description = "allows you to get all the books in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))),
            @ApiResponse(responseCode = "404", description = "books not found")})
    public Page<BookResponse> getAllBook(final Pageable pageable) {
        Page<Book> books = bookElasticsearchService.getAllBook(pageable);
        if (books.getContent().isEmpty()) {
            throw new ResourceNotFoundException("exception.notFound");
        }
        List<BookResponse> bookResponseList = bookElasticsearchMapper.booksToBooksResponse(books.getContent());
        return Utils.pageEntityToPageResponse(books, bookResponseList);
    }

    @GetMapping("/elasticsearch-book/aggregation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BooksResponse.class)))),
            @ApiResponse(responseCode = "404", description = "books not found")})
    public BooksResponse getBookWithAggregation(@RequestParam(defaultValue = "0") int more,
                                                @RequestParam(defaultValue = "5") int less,
                                                @RequestParam(defaultValue = "bookCondition") String field,
                                                final Pageable pageable) {
        return bookElasticsearchService.searchRangeFieldPriceAndAggregation(more, less, field, pageable);
    }

    @GetMapping("/elasticsearch-book/{id}")
    @Operation(summary = "get a certain book from elasticsearch",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse getBookById(@PathVariable @Parameter(description = "Book id") String id) {
        return bookElasticsearchMapper.bookElasticToBookElasticResponse(bookElasticsearchService.getBookById(id));
    }

    @GetMapping("/elasticsearch-book/name/{name}")
    @Operation(summary = "get a certain book by name from elasticsearch",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse findByName(@PathVariable @Parameter(description = "Book name") String name) {
        return bookElasticsearchMapper.bookElasticToBookElasticResponse(bookElasticsearchService.findByName(name));
    }

    @GetMapping("/elasticsearch-book/author/{author}")
    @Operation(summary = "get all books by author from elasticsearch",
            description = "allows you to get all the books by author in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public List<BookResponse> findByAuthors(@PathVariable @Parameter(description = "Book name") String author) {
        return bookElasticsearchMapper.booksToBooksResponse(bookElasticsearchService.findByAuthors(author));
    }

    @PostMapping("/elasticsearch-book")
    @Operation(summary = "add new book in elasticsearch", description = "allows you to add new book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "book created",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "book not add")})
    public BookResponse addBook(@RequestBody BookRequest bookRequest) {
        Book book = bookElasticsearchMapper.bookElasticRequestToBookElastic(bookRequest);
        return bookElasticsearchMapper.bookElasticToBookElasticResponse(bookElasticsearchService.addBook(book));
    }

    @DeleteMapping("/elasticsearch-book/{id}")
    @Operation(summary = "delete book from elasticsearch", description = "allows you to delete book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "book not delete")})
    public void deleteBookById(@PathVariable @Parameter(description = "Book id") String id) {
        bookElasticsearchService.deleteBookById(id);
    }

    @PutMapping("/elasticsearch-book/{id}")
    @Operation(summary = "update a certain book in elasticsearch",
            description = "allows you to update a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse updateBookById(@RequestBody BookRequest bookRequest,
                                       @Parameter(description = "Book id") @PathVariable String id) {
        return bookElasticsearchMapper.bookElasticToBookElasticResponse(
                bookElasticsearchService.updateBookById(bookRequest, id));
    }
}
