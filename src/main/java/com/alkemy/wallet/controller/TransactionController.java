package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TransactionController {

  @Autowired
  private ITransactionService transactionService;
  @GetMapping("/transactions/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(@PathVariable Long userId)
  {
    List<TransactionDto> transactionsList=transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }

  @PutMapping("/transactions/{userId}")
  public ResponseEntity<TransactionDto> updateTransaction (@PathVariable Long id, @RequestBody TransactionDto transactionDto){
    TransactionDto updatedTransaction= transactionService.updateTransaction(id, transactionDto);
    return ResponseEntity.ok().body(updatedTransaction);
  }
  @PostMapping("/sendArs")
  public ResponseEntity<TransactionDto> sendArs(@RequestBody TransactionDto transactionDto){
    TransactionDto transaction = ITransactionService.send(transactionDto, Currency.ARS);
    return ResponseEntity.ok().body(transaction);
  }
  @PostMapping("/sendUsd")
  public ResponseEntity<TransactionDto> sendUsd(@RequestBody TransactionDto transactionDto){
    TransactionDto transaction = ITransactionService.send(transactionDto, Currency.USD);
    return ResponseEntity.ok().body(transaction);
  }
}
