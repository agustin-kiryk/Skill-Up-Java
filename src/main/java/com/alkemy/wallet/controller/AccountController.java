package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.CurrencyDto;
import com.alkemy.wallet.dto.PageDto;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private IUserService iuserService;

  private IAccountService iAccountService;

  @Autowired
  AccountController(IAccountService iAccountService) {
    this.iAccountService = iAccountService;
  }

  @ApiOperation(value = "Get accounts by id", notes = "returns the accounts of an user as per the user id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized-you are not an Admin or you are not logged as the correct User"),
      @ApiResponse(code = 404, message = "Not found - The user was not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{userId}")
  public ResponseEntity<List<AccountDto>> getAccountById(
      @PathVariable("userId") @ApiParam(name = "userId", value = "User id", example = "1") Long userId) {
    List<AccountDto> listAccounts = this.iAccountService.findAllByUser(userId);
    return ResponseEntity.ok().body(listAccounts);
  }

  @ApiOperation(value = "Get balance", notes = "returns the balance of the accounts of a logged user")
  @ApiResponse(code = 200, message = "Successfully retrieved")
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/balance")
  public ResponseEntity<List<AccountBasicDto>> getBalance() {

    List<AccountBasicDto> accounts = iuserService.getAccountsBalance();
    return ResponseEntity.ok(accounts);
  }

  @ApiOperation(value = "create account", notes = "creates and return and account")
  @ApiResponse(code = 201, message = "Successfully created")
  @PreAuthorize("hasRole('USER')")
  @PostMapping()
  public ResponseEntity<Object> createAccount(
      @RequestBody CurrencyDto currency) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Object accountDto = this.iAccountService.addAccount(email, currency);//TODO : VER OBJECT
    return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
  }

  @ApiOperation(value = "update by id", notes = "updates and returns an account as per the account id")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized- you are not logged as the correct User")
  })
  @PreAuthorize("hasRole('USER')")
  @PutMapping("/{id}")
  public ResponseEntity<AccountDto> updateAccount(
      @PathVariable @ApiParam(name = "id", value = "Account id", example = "1") Long id,
      @RequestParam @ApiParam(name = "transactionLimit", value = "transaction limit", example = "5000") Double transactionLimitUpdated) {
    AccountDto dto = iAccountService.updateAccount(id, transactionLimitUpdated);
    return ResponseEntity.ok().body(dto);
  }

  @ApiOperation(value = "Get all accounts", notes = "returns all the accounts in the server")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 401, message = "Unauthorized-you are not an Admin")
  })
  @GetMapping()
  public ResponseEntity<PageDto<AccountDto>> getAllAccount(
      @PageableDefault(size = 10) Pageable page, HttpServletRequest request) {
    PageDto<AccountDto> pageDto = iAccountService.findAllAccounts(page, request);
    return ResponseEntity.ok().body(pageDto);
  }

}
