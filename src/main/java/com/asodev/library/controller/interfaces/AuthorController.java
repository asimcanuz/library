package com.asodev.library.controller.interfaces;

import com.asodev.library.dto.AuthorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorController {
    ResponseEntity<AuthorDTO> createAuthor(AuthorDTO authorDTO);

    ResponseEntity<AuthorDTO> getAuthorById(Long id);

    ResponseEntity<List<AuthorDTO>> getAllAuthors();

    ResponseEntity<AuthorDTO> updateAuthor(Long id, AuthorDTO authorDTO);

    ResponseEntity<Void> deleteAuthor(Long id);
}
