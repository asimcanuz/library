package com.asodev.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;
    private LocalDate yearPublished;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {
    }

    public Book(Long id, String title, LocalDate yearPublished, int stock) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getYearPublished() {
        return yearPublished;
    }

    public Book setYearPublished(LocalDate yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Book setStock(int stock) {
        this.stock = stock;
        return this;
    }


}
