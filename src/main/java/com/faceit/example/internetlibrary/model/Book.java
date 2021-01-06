package com.faceit.example.internetlibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "book_condition")
    private String bookCondition;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<OrderBook> orderBookList;

    public Book() {
    }

    public Book(String name, String bookCondition, String description) {
        this.name = name;
        this.bookCondition = bookCondition;
        this.description = description;
    }

    public void addOrderBookToBook(OrderBook orderBook) {
        if (orderBookList == null) {
            orderBookList = new ArrayList<>();
        }
        orderBookList.add(orderBook);
        orderBook.setBook(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrderBook> getOrderBookList() {
        return orderBookList;
    }

    public void setOrderBookList(List<OrderBook> orderBookList) {
        this.orderBookList = orderBookList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("bookCondition='" + bookCondition + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
