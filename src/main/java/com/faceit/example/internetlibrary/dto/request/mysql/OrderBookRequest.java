package com.faceit.example.internetlibrary.dto.request.mysql;

import com.faceit.example.internetlibrary.dto.response.mysql.BookResponse;
import com.faceit.example.internetlibrary.dto.response.mysql.UserResponse;
import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Order book request")
@Data
public class OrderBookRequest {

    @Schema(description = "Book status in the library", example = "IN_LIBRARY")
    private Status status;

    @NotNull(message = "exception.userDoesNotExist")
    private UserResponse user;

    @NotNull(message = "exception.bookDoesNotExist")
    private BookResponse book;

    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "date of taking the book")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "book return date")
    private LocalDateTime endDate;
}
