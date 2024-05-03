package com.asodev.library.controller;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.CreateAuthorDTO;
import com.asodev.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/authors")
public class AuthorController  {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Yeni yazar ekle")
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody CreateAuthorDTO createAuthorDto) {
        AuthorDTO createdAuthor = authorService.createAuthor(createAuthorDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Yazar Listesi ekle")
    @PostMapping("/list")
    public ResponseEntity<?> createAuthorWithList(@RequestBody List<CreateAuthorDTO> createAuthorDTOList){
        for (CreateAuthorDTO createAuthorDTO : createAuthorDTOList){
            authorService.createAuthor(createAuthorDTO);
        }

        return new ResponseEntity<>("Yazarlar Eklendi",HttpStatus.CREATED);
    }

    @Operation(summary = "Id ile yazar getir")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Operation(summary = "Bütün yazarları getir")
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Yazar güncelle")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody CreateAuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @Operation(summary = "Author sil")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}