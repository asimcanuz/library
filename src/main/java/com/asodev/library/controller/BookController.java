package com.asodev.library.controller;

import com.asodev.library.dto.BookDTO;
import com.asodev.library.dto.CreateBookDTO;
import com.asodev.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Id ile kitap getir")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @Operation(summary = "Kitapları getir")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Kitapları ismine göre getir")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<BookDTO>> getBooksByName(@PathVariable String name) {
        List<BookDTO> books = bookService.getBooksByName(name);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Kitapları yazara göre getir")
    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthorName(@RequestParam String authorName) {
        List<BookDTO> books = bookService.getBooksByAuthorName(authorName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Kitapları yazar ve isme göre getir")
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String searchTerm) {
        List<BookDTO> books = bookService.searchBooks(searchTerm);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Kitap oluştur")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Kitap güncelle")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody CreateBookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Operation(summary = "Kitap sil")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
