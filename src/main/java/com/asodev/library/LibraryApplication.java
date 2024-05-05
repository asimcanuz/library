package com.asodev.library;

import com.asodev.library.dto.AuthorDTO;
import com.asodev.library.dto.CreateAuthorDTO;
import com.asodev.library.dto.CreateBookDTO;
import com.asodev.library.dto.SignupRequest;
import com.asodev.library.model.Role;
import com.asodev.library.service.AuthorService;
import com.asodev.library.service.BookService;
import com.asodev.library.service.JwtService;
import com.asodev.library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserService userService,
            BookService bookService,
            AuthorService authorService
    ) {


        return args -> {
            var admin = new SignupRequest(
                    "admin",
                    "admin",
                    "admin",
                    "admin",
                    "admin@mail.com",
                    Set.of(Role.ROLE_ADMIN)
            );
            userService.createUser(admin);

            var user = new SignupRequest(
                    "user",
                    "user",
                    "user",
                    "user",
                    "user@mail.com",
                    Set.of(Role.ROLE_ADMIN)
            );
            userService.createUser(user);

            var author = new CreateAuthorDTO("author1");
            AuthorDTO authorDTO = authorService.createAuthor(author);

            var book1 = new CreateBookDTO(
                    "BOOK1",
                    LocalDate.now(),
                    100,
                    authorDTO.getId()
            );
            bookService.createBook(book1);


        };
    }

}
