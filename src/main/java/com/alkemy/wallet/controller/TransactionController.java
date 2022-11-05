package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> deposit(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.DEPOSIT.name()));
      return new ResponseEntity<>(transactionService.save(transactionDto), HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.PAYMENT.name()));
      return new ResponseEntity<>(transactionService.save(transactionDto), HttpStatus.CREATED);
    }
    @PostMapping("/income")
    public ResponseEntity<?> incomen(@RequestBody TransactionDto transactionDto){
      transactionDto.setTypeTransaction(TypeTransaction.valueOf(TypeTransaction.INCOME.name()));
      return new ResponseEntity<>(transactionService.save(transactionDto), HttpStatus.CREATED);
    }
  }

