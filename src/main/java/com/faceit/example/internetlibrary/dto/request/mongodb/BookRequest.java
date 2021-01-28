package com.faceit.example.internetlibrary.dto.request.mongodb;

import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "BookMongo request")
@Data
public class BookRequest {

    private String url;
    private String imageUrl;
    private String name;
    private List<String> authors;
    private String description;
    @Enumerated(EnumType.STRING)
    private BookCondition bookCondition;
    private double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
}
