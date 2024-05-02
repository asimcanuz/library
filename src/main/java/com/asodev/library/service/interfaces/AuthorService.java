package com.asodev.library.service.interfaces;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.CreateAuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(CreateAuthorDTO createAuthorDto);

    AuthorDTO getAuthorById(Long id);

    List<AuthorDTO> getAllAuthors();

    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthor(Long id);

}
