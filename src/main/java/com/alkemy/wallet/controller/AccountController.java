package com.alkemy.wallet.controller;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private IUserService userService;

  @Autowired
  private IAccountService accountService;

  @GetMapping("/balance")
  public ResponseEntity<List<AccountBasicDto>> getBalance(@Valid @PathVariable Long id) {

    List<AccountBasicDto> accounts = userService.getAccountsBalance(id);
    return ResponseEntity.ok(accounts);
  }


}
