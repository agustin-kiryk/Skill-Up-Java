package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

  @RestController
  @RequestMapping("/transactions")
  public class TransactionController {

    private final ITransactionService transactionService;

    @Autowired
    public TransactionController(ITransactionService transactionService) {
      this.transactionService = transactionService;
    }



    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> Deposit(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.DEPOSIT.name()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<TransactionDto> payment(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.PAYMENT.name()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/income")
    public ResponseEntity<TransactionDto> income(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.INCOME.name()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }
    @Autowired
    private ITransactionService itransactionService;

    @GetMapping(value = "/detail/{id}")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<?> getTransactionDetailById(@PathVariable("id") Integer id) throws Exception {
      return ResponseEntity.ok(transactionService.getTransactionDetailById(id));
    }

    @GetMapping(value = "/all/{userId}")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<List<TransactionDto>> listTransactions(@PathVariable Integer userId) {
      return ResponseEntity.ok(transactionService.getTransactions(userId));
    }
  }
