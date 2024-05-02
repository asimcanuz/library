package com.asodev.library.service;


import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.BookDTO;
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

    private BookDTO convertToDto(Book book){
        return modelMapper.map(book,BookDTO.class);
    }
    private Book convertToEntity(BookDTO bookDTO){
        return modelMapper.map(bookDTO,Book.class);
    }

    @Override
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id){
        Book book = getBook(id);
        return convertToDto(book);
    }
    @Override
    public List<BookDTO> getBooksByName(String title){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByAuthorName(String authorFirstName, String authorLastName) {
        List<Book> books = bookRepository.findByAuthorFirstNameAndAuthorLastNameIgnoreCase(authorFirstName, authorLastName);
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooks(String searchParam){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(searchParam,searchParam,searchParam);
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        book = bookRepository.save(book);
        return convertToDto(book);
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
        return convertToDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBook(id);
        book.setDeleted(true);
        bookRepository.save(book);
    }
    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "id'li kitap bulunamadı."));
    }
}
