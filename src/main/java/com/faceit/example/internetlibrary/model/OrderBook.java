package com.faceit.example.internetlibrary.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity(name = "order_book")
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private long reader;
    private long book;
    private String note;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public OrderBook() {
    }

    public OrderBook(Status status, long reader, long book, String note, LocalDateTime startDate, LocalDateTime endDate) {
        this.status = status;
        this.reader = reader;
        this.book = book;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getReader() {
        return reader;
    }

    public void setReader(long reader) {
        this.reader = reader;
    }

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderBook.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("status=" + status)
                .add("reader=" + reader)
                .add("book=" + book)
                .add("note='" + note + "'")
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
