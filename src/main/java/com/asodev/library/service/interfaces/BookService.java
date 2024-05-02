package com.asodev.library.service.interfaces;

import com.asodev.library.dto.BookDTO;
import com.asodev.library.dto.CreateBookDTO;

import java.util.List;

public interface BookService {
    BookDTO getBookById(Long id);

    List<BookDTO> getAllBooks();

    List<BookDTO> getBooksByName(String name);

    List<BookDTO> getBooksByAuthorName(String authorFirstName, String authorLastName);

    List<BookDTO> searchBooks(String searchTerm);

    BookDTO createBook(CreateBookDTO createBookDTO);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBook(Long id);
}