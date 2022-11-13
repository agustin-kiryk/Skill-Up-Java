package com.alkemy.wallet.controller;

import com.alkemy.wallet.auth.service.JwtUtils;
import com.alkemy.wallet.dto.PageDto;
import com.alkemy.wallet.dto.ResponseTransactionDto;
import com.alkemy.wallet.dto.SendTransferDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.service.ITransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.PublicKey;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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


  @ApiOperation(value = "Get transactions by id", notes = "Returns a list of transactions as per the User id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized - you are not an Admin"),
      @ApiResponse(code = 404, message = "Not found - The user was not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{userId}")
  public ResponseEntity<List<TransactionDto>> getTransactionsById(
      @PathVariable @ApiParam(name = " userId", value = "User id", example = "1") Long userId) {
    List<TransactionDto> transactionsList = transactionService.transactionsById(userId);
    return ResponseEntity.ok().body(transactionsList);

  }

  @ApiOperation(value = "make a payment", notes = "Makes a payment and return the transaction")
  @ApiResponse(code = 200, message = "Successfully retrieved")
  @PostMapping("/payment")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<TransactionDto> makeAPayment(@RequestBody TransactionDto dto) {

    dto.setType(TypeTransaction.PAYMENT);
    TransactionDto transactionDto = transactionService.createTransaction(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
  }

  @ApiOperation(value = "Get transaction by id", notes = "Returns a transaction as per the Transaction id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized - you are not logged as the user that owns the transaction"),
      @ApiResponse(code = 404, message = "Not found - The transaction was not found")
  })
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/{transactionId}")

  public ResponseEntity<TransactionDto> getDetailTransaction(
      @PathVariable @ApiParam(name = " transactionId", value = "Transaction id", example = "1") Long transactionId) {
    TransactionDto transaction = this.transactionService.getDetailById(transactionId);
    return ResponseEntity.ok(transaction);
  }

  @ApiOperation(value = "update transaction by id", notes = "update and returns the transaction as per the transaction Id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully updated"),
      @ApiResponse(code = 401, message = "Unauthorized - you are not logged as the user that owns the transaction"),
      @ApiResponse(code = 404, message = "Not found - The transaction was not found")
  })
  @PreAuthorize("hasRole('USER')")
  @PutMapping("/{id}")
  public ResponseEntity<TransactionDto> updateTransaction(
      @PathVariable @ApiParam(name = " id", value = "Transaction id", example = "1") Long id,
      @RequestBody TransactionDto transactionDto) {
    TransactionDto transactionUpdatedDto = transactionService.refreshValues(id, transactionDto);
    return ResponseEntity.ok().body(transactionUpdatedDto);
  }

  @ApiOperation(value = "deposit", notes = "creates and returns a deposit")
  @ApiResponse(code = 201, message = "Successfully created")
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/transactions/deposit")
  public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto dto) {
    TransactionDto newDeposit = transactionService.createNewDeposit(dto);
    return ResponseEntity.ok().body(dto);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/sendArs")
  public ResponseEntity<TransactionDto> sendArs(@RequestBody SendTransferDto sendTransferDto) {
    TransactionDto result = transactionService.send(sendTransferDto, Currency.ARS);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/sendUsd")
  public ResponseEntity<TransactionDto> sendUsd(@RequestBody SendTransferDto sendTransferDto) {
    TransactionDto result = transactionService.send(sendTransferDto, Currency.USD);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/user")
  public ResponseEntity<PageDto<TransactionDto>> getAllTransactionsByUserIdPaginated(
      @PageableDefault(size = 10) Pageable page, HttpServletRequest request,
      @RequestParam Long id) {
    PageDto<TransactionDto> pageDto = transactionService.findAllTransaction(page, request, id);
    return ResponseEntity.ok().body(pageDto);

  }


}
