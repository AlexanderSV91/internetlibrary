package com.faceit.example.internetlibrary.controller;

import com.faceit.example.internetlibrary.mapper.mysql.BookMapper;
import com.faceit.example.internetlibrary.service.mongodb.BookMongoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
@Tag(name = "BookMongo", description = "interaction with books in mongodb")
@SecurityRequirement(name = "basic")
public class BookMongoControllerRest {

    private final BookMongoService bookMongoService;
    private final BookMapper bookMapper;

    @Autowired
    public BookMongoControllerRest(BookMongoService bookMongoService, BookMapper bookMapper) {
        this.bookMongoService = bookMongoService;
        this.bookMapper = bookMapper;
    }


}
