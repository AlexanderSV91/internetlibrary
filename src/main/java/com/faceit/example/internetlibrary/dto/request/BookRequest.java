package com.faceit.example.internetlibrary.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookRequest {

    @NotBlank(message = "Please provide a book name")
    private String name;

    private String bookCondition;

    private String description;
}
