package com.faceit.example.internetlibrary.dto.response;

import lombok.Data;

@Data
public class BookResponse {

    private long id;
    private String name;
    private String bookCondition;
    private String description;
}
