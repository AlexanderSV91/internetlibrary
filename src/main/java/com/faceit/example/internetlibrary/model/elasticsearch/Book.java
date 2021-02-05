package com.faceit.example.internetlibrary.model.elasticsearch;

import com.faceit.example.internetlibrary.model.enumeration.BookCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "BookElasticsearch essence")
@Document(indexName = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    private List<String> authors;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Field(type = FieldType.Keyword, name = "bookCondition")
    private BookCondition bookCondition;

    @Field(type = FieldType.Double, name = "price")
    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
}
