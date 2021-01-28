package com.faceit.example.internetlibrary.model.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Schema(description = "Book essence")
@Entity(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier")
    private long id;

    @Column(name = "name")
    @Schema(description = "Book name", example = "In Search of Lost")
    private String name;

    @Column(name = "book_condition")
    @Schema(example = "Excellent")
    private String bookCondition;

    @Column(name = "description")
    private String description;
}
