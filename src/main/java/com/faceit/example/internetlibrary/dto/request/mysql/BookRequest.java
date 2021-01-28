package com.faceit.example.internetlibrary.dto.request.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "Book request")
@Data
public class BookRequest {

    @NotBlank(message = "exception.pleaseProvideABookName")
    @Schema(description = "Book name", example = "In Search of Lost")
    private String name;

    @NotBlank(message = "exception.pleaseProvideABookCondition")
    @Schema(example = "Excellent")
    private String bookCondition;

    private String description;
}
