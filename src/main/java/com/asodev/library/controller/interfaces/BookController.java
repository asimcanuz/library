package com.asodev.library.controller.interfaces;

import com.asodev.library.dto.BookDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface BookController {

    ResponseEntity<BookDTO> getBookById(Long id);

    ResponseEntity<List<BookDTO>> getAllBooks();

    ResponseEntity<List<BookDTO>> getBooksByName(String name);

    ResponseEntity<List<BookDTO>> getBooksByAuthorName(String authorFirstName, String authorLastName);

    ResponseEntity<List<BookDTO>> searchBooks(String searchTerm);

    ResponseEntity<BookDTO> createBook(BookDTO bookDTO);

    ResponseEntity<BookDTO> updateBook(Long id, BookDTO bookDTO);

    ResponseEntity<Void> deleteBook(Long id);
}

