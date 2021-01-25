package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.dto.request.BookRequest;
import com.faceit.example.internetlibrary.dto.response.BookResponse;
import com.faceit.example.internetlibrary.exception.ResourceNotFoundException;
import com.faceit.example.internetlibrary.mapper.BookMapper;
import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Books", description = "interaction with books")
@SecurityRequirement(name = "basic")
public class BookControllerRest {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookControllerRest(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/book")
    @Operation(summary = "get all books",
            description = "allows you to get all the books in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookResponse.class)))),
            @ApiResponse(responseCode = "404", description = "books not found")})
    public List<BookResponse> getAllBook() {
        List<Book> books = bookService.getAllBook();
        if (books != null) {
            return books.stream().map(bookMapper::bookToBookResponse).collect(Collectors.toList());
        }
        throw new ResourceNotFoundException("exception.notFound");
    }

    @GetMapping("/book/{id}")
    @Operation(summary = "get a certain book",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse getBookById(@PathVariable @Parameter(description = "Book id") long id) {
        return bookMapper.bookToBookResponse(bookService.getBookById(id));
    }

    @PostMapping("/book")
    @Operation(summary = "add new book", description = "allows you to add new book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "book created",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "book not add")})
    public BookResponse addBook(@AuthenticationPrincipal MyUserDetails userDetails,
                                @Valid @RequestBody BookRequest bookRequest) {
        Book book = bookMapper.bookRequestToBook(bookRequest);
        return bookMapper.bookToBookResponse(bookService.addBook(book, userDetails.getUser().getRoles()));
    }

    @DeleteMapping("/book/{id}")
    @Operation(summary = "delete book", description = "allows you to delete book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "book not delete")})
    public void deleteBookById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @PathVariable @Parameter(description = "Book id") long id) {
        bookService.deleteBookById(id, userDetails.getUser().getRoles());
    }

    @PutMapping("/book/{id}")
    @Operation(summary = "update a certain book",
            description = "allows you to update a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public BookResponse updateBookById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @RequestBody BookRequest bookRequest,
                               @Parameter(description = "Book id") @PathVariable Long id) {
        Book book = bookMapper.bookRequestToBook(bookRequest);
        return bookMapper.bookToBookResponse(
                bookService.updateBookById(book, id, userDetails.getUser().getRoles()));
    }
}
