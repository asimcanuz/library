package com.asodev.library.repository;

import com.asodev.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByIdAndDeletedFalse(Long id);
    List<Book> findAllByDeletedFalse();
    List<Book> findByTitleContainingIgnoreCaseAndDeletedFalse(String title);

    List<Book> findByAuthorFirstNameAndAuthorLastNameIgnoreCaseAndDeletedFalse(String authorFirstName, String authorLastName);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCaseAndDeletedFalse(String title, String authorFirstName, String authorLastName);

}
