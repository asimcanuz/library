package com.asodev.library.controller;

import com.asodev.library.model.Loan;
import com.asodev.library.repository.LoanRepository;
import com.asodev.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Id ile ödünç getir")
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id){
        Loan loan = loanService.getLoanById(id);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @Operation(summary = "Bütün ödünç verilen kitapları getir")
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans(){
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Operation(summary = "Yeni loan oluştur")
    @PostMapping
    public ResponseEntity<Loan> createLoan(Loan loan){
        Loan newLoan = loanService.createLoan(loan);
        return new ResponseEntity<>(newLoan,HttpStatus.CREATED);
    }

    @Operation(summary = "Loan güncelle")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody Loan loan){
        try{
            Loan updatedLoan = loanService.updateLoan(id,loan);
            return new ResponseEntity<>(updatedLoan,HttpStatus.OK);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Loan sil")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id){
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
