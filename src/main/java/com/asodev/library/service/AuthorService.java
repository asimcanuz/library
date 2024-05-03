package com.asodev.library.service;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.CreateAuthorDTO;
import com.asodev.library.model.Author;
import com.asodev.library.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }


    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDto) {
        Author author = modelMapper.map(createAuthorDto,Author.class);
        author = authorRepository.save(author);
        return modelMapper.map(author,AuthorDTO.class);
    }


    public AuthorDTO getAuthorById(Long id) {
        Author author = getAuthor(id);
        return modelMapper.map(author,AuthorDTO.class);
    }


    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAllByDeletedFalse();
        return authors.stream().map(author -> modelMapper.map(author,AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = getAuthor(id);
        author.setName(author.getName());
        author = authorRepository.save(author);
        return modelMapper.map(author,AuthorDTO.class);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthor(id);
        author.setDeleted(true);
        authorRepository.save(author);
    }


    private Author getAuthor(Long id){
        return authorRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new IllegalArgumentException(id+" id'li yazar bulunamadı!"));
    }
}
