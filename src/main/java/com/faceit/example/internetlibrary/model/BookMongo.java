package com.faceit.example.internetlibrary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "BookMongo essence")
@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookMongo {

    @Id
    private String id;

    private String url;
    private String imageUrl;
    private String name;
    private List<String> authors;
    private String description;
    private double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
}
