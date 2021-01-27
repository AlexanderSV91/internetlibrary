package com.faceit.example.internetlibrary.dto.response.mysql;

import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Schema(description = "Order book response")
@Data
public class OrderBookResponse {

    @Schema(description = "Identifier")
    private long id;

    @Schema(description = "Book status in the library", example = "IN_LIBRARY")
    @Enumerated(EnumType.STRING)
    private Status status;
    private UserResponse user;
    private BookResponse book;
    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "date of taking the book")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "book return date")
    private LocalDateTime endDate;
}
