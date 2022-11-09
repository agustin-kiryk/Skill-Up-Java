package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.AccountBasicDto;

import java.util.List;

public interface IAccountService {

  static Object getAccountBalance(Long idUser) {
    return null;
  }

  AccountBasicDto findById(Long accountId);

  double calculateBalance(Long accountId);

  List<AccountDto> findAllByUser(Long userId);

  void updateBalance(Long accountId, Double amount);

  //AccountEntity findEntityById (Long accountId);

  AccountDto createAccount(String currency);
}
