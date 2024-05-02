package com.asodev.library.service;


import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.BookDTO;
import com.asodev.library.dto.CreateBookDTO;
import com.asodev.library.model.Author;
import com.asodev.library.model.Book;
import com.asodev.library.repository.BookRepository;
import com.asodev.library.service.interfaces.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private  final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookRepository.findAllByDeletedFalse();
        return books.stream().map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id){
        Book book = getBook(id);
        return modelMapper.map(book,BookDTO.class);
    }
    @Override
    public List<BookDTO> getBooksByName(String title){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseAndDeletedFalse(title);
        return books.stream()
                .map(book -> this.modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByAuthorName(String authorFirstName, String authorLastName) {
        List<Book> books = bookRepository.findByAuthorFirstNameAndAuthorLastNameIgnoreCaseAndDeletedFalse(authorFirstName, authorLastName);
        return books.stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooks(String searchParam){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCaseAndDeletedFalse(searchParam,searchParam,searchParam);
        return books.stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public BookDTO createBook(CreateBookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO,Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = getBook(id);
        // Update book properties with new values
        book.setTitle(bookDTO.getTitle());
        book.setYearPublished(bookDTO.getYearPublished());
        book.setStock(bookDTO.getStock());
        // author
        AuthorDTO authorDTO = bookDTO.getAuthor();
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());

        book.setAuthor(author);
        // Save the updated book
        book = bookRepository.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBook(id);
        book.setDeleted(true);
        bookRepository.save(book);
    }
    private Book getBook(Long id) {
        return bookRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "id'li kitap bulunamadı."));
    }
}
