package com.faceit.example.internetlibrary.model.mongodb;

import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "BookMongo essence")
@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MongoBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
