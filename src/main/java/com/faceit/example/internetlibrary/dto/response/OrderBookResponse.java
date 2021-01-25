package com.faceit.example.internetlibrary.dto.response;

import com.faceit.example.internetlibrary.model.Book;
import com.faceit.example.internetlibrary.model.User;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Order book response")
@Data
public class OrderBookResponse {

    @Schema(description = "Identifier")
    private long id;

    @Schema(description = "Book status in the library", example = "IN_LIBRARY")
    private Status status;
    private User user;
    private Book book;
    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "date of taking the book")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "book return date")
    private LocalDateTime endDate;
}
