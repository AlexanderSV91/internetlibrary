package com.faceit.example.internetlibrary.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Schema(description = "User essence")
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier")
    private long id;

    @Column(name = "username")
    @NotBlank(message = "Please provide a username")
    private String userName;

    @Column(name = "password")
    @Schema(example = "~w_3?d7`z-91&")
    private String password;

    @Column(name = "first_name")
    @NotBlank(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Please provide your last name")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Please provide a valid Email")
    @NotBlank(message = "Please provide an email")
    @Schema(description = "Email address of the contact.", example = "alexander@gmail.com")
    private String email;

    @Column(name = "age")
    private int age;

    @Hidden
    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
