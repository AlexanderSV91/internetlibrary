package com.faceit.example.internetlibrary.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Schema(description = "Role essence")
@Entity(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier")
    private long id;

    @Column(name = "name")
    @Schema(example = "ROLE_USER")
    private String name;
}
