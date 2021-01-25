package com.faceit.example.internetlibrary.dto.response;

import lombok.Data;

@Data
public class UserResponse {

    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
