package com.faceit.example.internetlibrary.model.mysql;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Schema(description = "number authorization essence")
@Entity(name = "number_authorizations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NumberAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier")
    private long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "last_authorization_date")
    private LocalDateTime lastAuthorizationDate;
}
