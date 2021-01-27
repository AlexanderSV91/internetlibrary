package com.faceit.example.internetlibrary.dto.request.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "User request")
@Data
public class UserRequest {

    @NotBlank(message = "exception.pleaseProvideAUsername")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthUsername")
    private String userName;
    @Length(min = 5, max = 30, message = "exception.incorrectPassword")
    @Schema(example = "~w_3?d7`z-91&")
    private String password;
    @NotBlank(message = "exception.pleaseProvideYourFirstName")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthFirstName")
    private String firstName;
    @NotBlank(message = "exception.pleaseProvideYourLastName")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthLastName")
    private String lastName;
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "exception.pleaseProvideAValidEmail")
    @NotBlank(message = "exception.pleaseProvideAnEmail")
    @Schema(description = "Email address of the contact.", example = "alexander@gmail.com")
    private String email;
    private int age;
}
