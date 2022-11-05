package com.alkemy.wallet.controller;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @Autowired
  private IUserService userService;

  @Autowired
  private IAccountService accountService;

  @GetMapping("/account/balance")
  public ResponseEntity<List<AccountBasicDto>>(@Valid @PathVariable Long id) {

    UserDto user = userService.findById(id);
    List<AccountBasicDto> accounts = user.getAccounts();

    for(int i = 0; i < accounts.size(); i++){

      AccountBasicDto account;
      account = accounts.get(i);
      account.setBalance(accountService.calculateBalance(id);

    }

    return ResponseEntity.ok(accounts);
  }


}
