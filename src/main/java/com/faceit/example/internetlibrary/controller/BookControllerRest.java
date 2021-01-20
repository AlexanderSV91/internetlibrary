package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.configuration.MyUserDetails;
import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "Books", description = "interaction with books")
public class BookControllerRest {
    private final BookService bookService;

    @Autowired
    public BookControllerRest(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    @Operation(summary = "get all books",
            description = "allows you to get all the books in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class))))})
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    @Operation(summary = "get a certain book",
            description = "allows you to get a specific book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public Book getBookById(@PathVariable @Parameter(description = "Book id") long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/book")
    @Operation(summary = "add new book", description = "allows you to add new book in the library")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "book created",
            content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "book not add")})
    public Book addBook(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody Book newBook) {
        return bookService.addBook(newBook, userDetails.getUser().getRoles());
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
            content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "book not found")})
    public Book updateBookById(@AuthenticationPrincipal MyUserDetails userDetails,
                               @RequestBody Book updateBook,
                               @Parameter(description = "Book id") @PathVariable Long id) {
        return bookService.updateBookById(updateBook, id, userDetails.getUser().getRoles());
    }
}
