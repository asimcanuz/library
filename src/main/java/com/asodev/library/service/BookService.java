package com.asodev.library.service;

import com.asodev.library.dto.BookDTO;
import com.asodev.library.dto.CreateBookDTO;
import com.asodev.library.exception.ResourceNotFoundException;
import com.asodev.library.model.Author;
import com.asodev.library.model.Book;
import com.asodev.library.repository.AuthorRepository;
import com.asodev.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private  final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    public BookService(ModelMapper modelMapper, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> getAllBooks(){
        List<Book> books = bookRepository.findAllByDeletedFalse();
        return books.stream().map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id){
        Book book = getBook(id);
        return modelMapper.map(book,BookDTO.class);
    }
    public List<BookDTO> getBooksByName(String title){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseAndDeletedFalse(title);
        return books.stream()
                .map(book -> this.modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByAuthorName(String authorName) {
        List<Book> books = bookRepository.findByAuthorNameIgnoreCaseAndDeletedFalse(authorName);
        return books.stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooks(String searchParam){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseAndDeletedFalse(searchParam,searchParam);
        return books.stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }


    public BookDTO createBook(CreateBookDTO bookDTO) {

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(()-> new ResourceNotFoundException("Yazar Bulunamadı."));

        Book book = modelMapper.map(bookDTO,Book.class);
        book.setAuthor(author);
        book = bookRepository.save(book);

        return modelMapper.map(book,BookDTO.class);
    }

    public BookDTO updateBook(Long id, CreateBookDTO bookDTO) {
        Book book = getBook(id);

        // Update book properties with new values
        book.setTitle(bookDTO.getTitle());
        book.setYearPublished(bookDTO.getYearPublished());
        book.setStock(bookDTO.getStock());

        // author
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(()-> new EntityNotFoundException("Yazar Bulunamadı"));
        book.setAuthor(author);

        // Save the updated book
        book = bookRepository.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    public void deleteBook(Long id) {
        Book book = getBook(id);
        book.setDeleted(true);
        bookRepository.save(book);
    }
    private Book getBook(Long id) {
        return bookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + "id'li kitap bulunamadı."));
    }
}
