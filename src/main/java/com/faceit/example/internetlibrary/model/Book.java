package com.faceit.example.internetlibrary.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "book_condition")
    private String bookCondition;
    @Column(name = "description")
    private String description;
}
