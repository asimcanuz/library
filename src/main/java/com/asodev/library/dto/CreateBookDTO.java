package com.asodev.library.dto;

import java.time.LocalDate;

public class CreateBookDTO {

    private String title;
    private LocalDate yearPublished;
    private int stock;
    private Long authorId;

    public CreateBookDTO() {
    }

    public CreateBookDTO(String title, LocalDate yearPublished, int stock, Long authorId) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.stock = stock;
        this.authorId = authorId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
