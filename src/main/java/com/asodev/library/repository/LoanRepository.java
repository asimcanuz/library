package com.asodev.library.repository;

import com.asodev.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<Loan> findByIdAndDeletedFalse(Long id);

    int countByUserIdAndDeletedFalse(Long userId);

    List<Loan> findAllByDeletedFalse();
}
