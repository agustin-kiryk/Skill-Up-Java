package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private IUserService iuserService;

  private IAccountService iAccountService;
  private AccountController accountService;

  AccountController(IAccountService iAccountService) {
    this.iAccountService = iAccountService;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<AccountDTO>> getAccountById(@PathVariable("UserId") Long userId) {
    List<AccountDTO> listAccounts = this.iAccountService.findAllByUser(userId);
    return ResponseEntity.ok().body(listAccounts);
  }

  @GetMapping("/balance")
  public ResponseEntity<List<AccountBasicDto>> getBalance(@Valid @PathVariable Long id) {

    List<AccountBasicDto> accounts = iuserService.getAccountsBalance(id);
    return ResponseEntity.ok(accounts);
  }

  @PostMapping("/create")
  public ResponseEntity<AccountDTO> createAccount(
      @RequestParam(required = false) int userId,
      @RequestParam(required = false) String currency) throws Exception {

    ResponseEntity<AccountDTO> accountDTO = accountService.createAccount(userId, currency);

    ResponseEntity<AccountDTO> body = null;
    return body;

  }
}
