package com.asodev.library.service;

import com.asodev.library.dto.CreateLoanDto;
import com.asodev.library.exception.ResourceNotFoundException;
import com.asodev.library.model.Book;
import com.asodev.library.model.Loan;
import com.asodev.library.model.User;
import com.asodev.library.repository.BookRepository;
import com.asodev.library.repository.LoanRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public Loan createLoan(CreateLoanDto createLoanDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        if (getNumberOfLoansByUser(userId)>3) {
            throw new RuntimeException("En fazla 3 kitap ödünç alabilirsiniz.");
        }

        Loan loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setUser(user);

        Book book = bookRepository.findById(createLoanDto.bookId())
                .orElseThrow(()-> new ResourceNotFoundException("Hatalı Kitap"));
        loan.setBook(book);


        return loanRepository.save(loan);
    }


    public Loan getLoanById(Long id) {
        return getLoan(id);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAllByDeletedFalse();
    }

    public void deleteLoan(Long id) {
        Loan loan = getLoan(id);
        loan.setDeleted(true);

        // eğer loan silinirse kitap loan false;
        Book book = loan.getBook();
        book.setLoaned(false);
        bookRepository.save(book);

        loanRepository.save(loan);
    }
    private Loan getLoan(Long id) {
        return loanRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(id + "id'li kitap bulunamadı."));
    }

    public Loan updateLoan(Long id, Loan loan){
        Loan existingLoan = getLoan(id);

        existingLoan.setLoanDate(loan.getLoanDate());
        existingLoan.setDueDate(loan.getDueDate());
        existingLoan.setReturnDate(loan.getReturnDate());

        return loanRepository.save(existingLoan);
    }

    public Loan returnLoan(Long id, Loan loan){
        Loan existingLoan = getLoan(id);

        existingLoan.setReturnDate(LocalDate.now());
        existingLoan.getBook().setLoaned(false);

        Book book = loan.getBook();
        book.setLoaned(true);
        bookRepository.save(book);

        return loanRepository.save(existingLoan);
    }


    private int getNumberOfLoansByUser(Long userId){
        return loanRepository.countByUserIdAndDeletedFalse(userId);
    }

}
