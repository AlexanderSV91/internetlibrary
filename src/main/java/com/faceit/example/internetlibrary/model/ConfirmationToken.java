package com.faceit.example.internetlibrary.model;

import com.faceit.example.internetlibrary.model.enumeration.TokenStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "confirmation_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TokenStatus status;

    @Column(name = "issued_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime issuedDate;
}
