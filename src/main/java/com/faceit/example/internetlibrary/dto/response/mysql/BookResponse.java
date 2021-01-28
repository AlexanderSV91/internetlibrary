package com.faceit.example.internetlibrary.dto.response.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Book response")
@Data
public class BookResponse {

    @Schema(description = "Identifier")
    private long id;

    @Schema(description = "Book name", example = "In Search of Lost")
    private String name;

    @Schema(example = "Excellent")
    private String bookCondition;
    private String description;
}
