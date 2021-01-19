package com.faceit.example.internetlibrary.model;

import com.faceit.example.internetlibrary.model.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "order_books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Order book essence")
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Schema(description = "Book status in the library", example = "IN_LIBRARY")
    private Status status;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "note")
    private String note;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "date of taking the book")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "book return date")
    private LocalDateTime endDate;
}
