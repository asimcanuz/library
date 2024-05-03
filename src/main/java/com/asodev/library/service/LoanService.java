package com.asodev.library.service;

import com.asodev.library.model.Loan;
import com.asodev.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loan) {
        if (getNumberOfLoansByUser(loan.getMember().getId())>3) {
            throw new RuntimeException("En fazla 3 kitap ödünç alabilirsiniz.");
        }
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
        loanRepository.save(loan);
    }
    private Loan getLoan(Long id) {
        return loanRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "id'li kitap bulunamadı."));
    }

    public Loan updateLoan(Long id, Loan loan){
        Loan existingLoan = getLoan(id);
        existingLoan.setLoanDate(loan.getLoanDate());
        existingLoan.setDueDate(loan.getDueDate());
        existingLoan.setReturnDate(loan.getReturnDate());

        return loanRepository.save(existingLoan);
    }


    private int getNumberOfLoansByUser(Long userId){
        return loanRepository.countByMemberIdAndDeletedFalse(userId);
    }

}
