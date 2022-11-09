package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.service.ITransactionService;
import java.security.PublicKey;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  RestExceptionHandler restExceptionHandler;
  @Autowired
  private ITransactionService transactionService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(@PathVariable Long userId) {
    List<TransactionDto> transactionsList = transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }

  @PostMapping("/payment")
  public ResponseEntity<TransactionDto> makeAPayment(@RequestBody TransactionDto dto) {

    dto.setType(TypeTransaction.PAYMENT);
    TransactionDto transactionDto = transactionService.createTransaction(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
  }

  @GetMapping("/{transactionId}")

  public ResponseEntity<TransactionDto> getDetailTransaction(@PathVariable Long transactionId) {
    TransactionDto transaction = this.transactionService.getDetailById(transactionId);
    return ResponseEntity.ok(transaction);
  }

  @PostMapping("/sendArs")
  public ResponseEntity<TransactionDto> sendArs(
      @Valid @RequestBody TransactionDto transactionSendMoneyDto) {
    TransactionDto transaction = transactionService.send(transactionSendMoneyDto, Currency.ARS);
    return ResponseEntity.ok().body(transaction);
  }

  @PostMapping("/sendUsd")
  public ResponseEntity<TransactionDto> sendUsd(
      @Valid @RequestBody TransactionDto transactionSendMoneyDto) {
    TransactionDto transaction = transactionService.send(transactionSendMoneyDto, Currency.ARS);
    return ResponseEntity.ok().body(transaction);
  }
}