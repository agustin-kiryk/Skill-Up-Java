package com.alkemy.wallet.controller;

import com.alkemy.wallet.auth.service.JwtUtils;
import com.alkemy.wallet.dto.ResponseTransactionDto;
import com.alkemy.wallet.dto.SendTransferDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  RestExceptionHandler restExceptionHandler;
  @Autowired
  private ITransactionService transactionService;

  private final JwtUtils jwtUtils;

  public TransactionController(ITransactionService transactionService, JwtUtils jwtUtils) {
    this.transactionService = transactionService;
    this.jwtUtils = jwtUtils;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(@PathVariable Long userId)
  {
    List<TransactionDto> transactionsList=transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }
  @PostMapping("/payment")
  public ResponseEntity<TransactionDto> makeAPayment(@RequestBody TransactionDto dto) {

      dto.setType(TypeTransaction.PAYMENT);
      TransactionDto transactionDto = transactionService.createTransaction(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
    }

    @GetMapping("/{transactionId}")

    public ResponseEntity<TransactionDto> getDetailTransaction(@PathVariable Long transactionId){
      TransactionDto transaction = this.transactionService.getDetailById(transactionId);
      return ResponseEntity.ok(transaction);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
      TransactionDto transactionUpdatedDto = transactionService.refreshValues(id, transactionDto);
      return ResponseEntity.ok().body(transactionUpdatedDto);
    }


  @PostMapping("/transactions/deposit")
  public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto dto){
    TransactionDto newDeposit = transactionService.createNewDeposit(dto);
    return ResponseEntity.ok().body(dto);
  }

  @PostMapping("/sendArs")
  public ResponseEntity<TransactionDto> sendArs(@RequestBody SendTransferDto sendTransferDto){
    TransactionDto result = transactionService.send(sendTransferDto, Currency.ARS);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }


}
