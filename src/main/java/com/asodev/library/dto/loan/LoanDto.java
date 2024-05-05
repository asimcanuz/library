package com.asodev.library.dto.loan;

import java.time.LocalDate;

public record LoanDto(
    Long id,
    String username,
    LoanBookDto book,
    boolean isLoaned,
    LocalDate loanDate,
    LocalDate loanDueDate,
    LocalDate loanReturnDate
) {
}
