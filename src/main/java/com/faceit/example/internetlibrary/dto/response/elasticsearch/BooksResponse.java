package com.faceit.example.internetlibrary.dto.response.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksResponse {
    private Page<BookResponse> bookResponses;
    private Map<String, Long> aggregation;
}
