package com.asodev.library.dto;

import com.asodev.library.model.Book;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateLoanDto(
        @NotBlank
        Long bookId
) {
}
