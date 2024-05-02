package com.asodev.library.dto;

import java.time.LocalDate;

public class CreateBookDTO {

    private String title;
    private LocalDate yearPublished;
    private int stock;
    private AuthorDTO author;

    public CreateBookDTO() {
    }

    public CreateBookDTO(String title, LocalDate yearPublished, int stock, AuthorDTO author) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.stock = stock;
        this.author = author;
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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
