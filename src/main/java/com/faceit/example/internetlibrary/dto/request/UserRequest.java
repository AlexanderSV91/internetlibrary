package com.faceit.example.internetlibrary.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @NotBlank(message = "Please provide a username")
    private String userName;
    private String password;
    @NotBlank(message = "Please provide your first name")
    private String firstName;
    @NotBlank(message = "Please provide your last name")
    private String lastName;
    @Email(message = "Please provide a valid Email")
    @NotBlank(message = "Please provide an email")
    private String email;
    private int age;
}
