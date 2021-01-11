package com.faceit.example.internetlibrary.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "reader")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
}
