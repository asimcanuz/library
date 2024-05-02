package com.asodev.library.repository;

import com.asodev.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByIdAndDeletedFalse(Long id);

    List<Author> findAllByDeletedFalse();
}
