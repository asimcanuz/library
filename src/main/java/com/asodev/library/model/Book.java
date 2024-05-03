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

    private boolean deleted = false;
    private boolean loaned = false;

    public Book() {
    }

    public Book(Long id, String title, LocalDate yearPublished, int stock, Author author, boolean deleted,boolean loaned) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.stock = stock;
        this.author = author;
        this.deleted = deleted;
        this.loaned = loaned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public LocalDate getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(LocalDate yearPublished) {
        this.yearPublished = yearPublished;

    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;

    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;

    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isLoaned() {
        return loaned;
    }

    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }
}
