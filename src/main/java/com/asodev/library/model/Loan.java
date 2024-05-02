package com.asodev.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;


    public Loan() {
    }

    public Loan(Long id, Member member, Book book, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Loan setId(Long id) {
        this.id = id;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public Loan setMember(Member member) {
        this.member = member;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Loan setBook(Book book) {
        this.book = book;
        return this;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public Loan setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Loan setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Loan setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}
