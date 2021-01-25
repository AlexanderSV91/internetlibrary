package com.faceit.example.internetlibrary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User response")
@Data
public class UserResponse {

    @Schema(description = "Identifier")
    private long id;
    private String userName;
    private String firstName;
    private String lastName;

    @Schema(description = "Email address of the contact.", example = "alexander@gmail.com")
    private String email;
    private int age;
}
