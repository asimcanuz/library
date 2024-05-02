package com.asodev.library.repository;

import com.asodev.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorFirstNameAndAuthorLastNameIgnoreCase(String authorFirstName, String authorLastName);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(String title, String authorFirstName, String authorLastName);

}
