package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.dto.request.mongodb.BookRequest;
import com.faceit.example.internetlibrary.dto.response.mongodb.BookResponse;
import com.faceit.example.internetlibrary.mapper.mongo.BookMongoMapper;
import com.faceit.example.internetlibrary.model.mongodb.MongoBook;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
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
@Tag(name = "BookMongo", description = "interaction with books in mongodb")
@SecurityRequirement(name = "basic")
public class BookMongoControllerRest {

    private final BookMongoService bookMongoService;
    private final BookMongoMapper bookMongoMapper;

    public BookMongoControllerRest(BookMongoService bookMongoService, BookMongoMapper bookMongoMapper) {
        this.bookMongoService = bookMongoService;
        this.bookMongoMapper = bookMongoMapper;
    }

    @GetMapping("/mongo-book")
    @Operation(summary = "get all books from mongodb",
            description = "allows you to get all the books in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))),
            @ApiResponse(responseCode = "404", description = "books not found")})
    public Page<BookResponse> getAllBook(final Pageable pageable) {
        return bookMongoService.getAllBookResponse(pageable);
    }

    @GetMapping("/mongo-book/{id}")
    @Operation(summary = "get a certain book from mongodb",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse getBookById(@PathVariable @Parameter(description = "Book id") String id) {
        return bookMongoMapper.bookMongoToBookMongoResponse(bookMongoService.getBookById(id));
    }

    @GetMapping("/mongo-book/name/{name}")
    @Operation(summary = "get a certain book by name from mongodb",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse findByName(@PathVariable @Parameter(description = "Book name") String name) {
        return bookMongoMapper.bookMongoToBookMongoResponse(bookMongoService.findByName(name));
    }

    @GetMapping("/mongo-book/author/{author}")
    @Operation(summary = "get all books by author from mongodb",
            description = "allows you to get all the books by author in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public List<BookResponse> findByAuthors(@PathVariable @Parameter(description = "Book name") String author) {
        return bookMongoMapper.booksToBooksResponse(bookMongoService.findByAuthors(author));
    }

    @PostMapping("/mongo-book")
    @Operation(summary = "add new book in mongodb", description = "allows you to add new book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "book created",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "book not add")})
    public BookResponse addBook(@RequestBody BookRequest bookRequest) {
        MongoBook book = bookMongoMapper.bookMongoRequestToBookMongo(bookRequest);
        return bookMongoMapper.bookMongoToBookMongoResponse(bookMongoService.addBook(book));
    }

    @DeleteMapping("/mongo-book/{id}")
    @Operation(summary = "delete book from mongodb", description = "allows you to delete book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "book not delete")})
    public void deleteBookById(@PathVariable @Parameter(description = "Book id") String id) {
        bookMongoService.deleteBookById(id);
    }

    @PutMapping("/mongo-book/{id}")
    @Operation(summary = "update a certain book in mongodb",
            description = "allows you to update a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse updateBookById(@RequestBody BookRequest bookRequest,
                                       @Parameter(description = "Book id") @PathVariable String id) {
        return bookMongoMapper.bookMongoToBookMongoResponse(
                bookMongoService.updateBookById(bookRequest, id));
    }
}
