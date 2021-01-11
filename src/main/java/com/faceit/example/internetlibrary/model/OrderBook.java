package com.faceit.example.internetlibrary.model;

import com.faceit.example.internetlibrary.model.enam.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "order_book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "reader")
    private Reader reader;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book")
    private Book book;
    @Column(name = "note")
    private String note;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
}
