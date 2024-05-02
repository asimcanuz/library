package com.asodev.library.service;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.CreateAuthorDTO;
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

    @Override
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDto) {
        Author author = modelMapper.map(createAuthorDto,Author.class);
        author = authorRepository.save(author);
        return modelMapper.map(author,AuthorDTO.class);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = getAuthor(id);
        return modelMapper.map(author,AuthorDTO.class);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAllByDeletedFalse();
        return authors.stream().map(author -> modelMapper.map(author,AuthorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = getAuthor(id);
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author = authorRepository.save(author);

        return modelMapper.map(author,AuthorDTO.class);
    }

    @Override
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
