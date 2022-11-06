package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class TransactionController {

  @Autowired
  private ITransactionService transactionService;
  @GetMapping("/transactions/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(@PathVariable Long userId)
  {
    List<TransactionDto> transactionsList=transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }

}
