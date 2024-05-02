package com.asodev.library.service;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.model.Author;
import com.asodev.library.repository.AuthorRepository;
import com.asodev.library.service.interfaces.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    private AuthorDTO convertToDto(Author author){
        return modelMapper.map(author,AuthorDTO.class);
    }
    private Author convertToEntity(AuthorDTO authorDTO){
        return modelMapper.map(authorDTO,Author.class);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        author = authorRepository.save(author);
        return convertToDto(author);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = getAuthor(id);
        return convertToDto(author);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = getAuthor(id);
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author = authorRepository.save(author);

        return convertToDto(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = getAuthor(id);
        author.setDeleted(true);
        authorRepository.save(author);
    }


    private Author getAuthor(Long id){
        return authorRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException(id+" id'li ürün bulunamadı!"));
    }
}
