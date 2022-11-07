package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.entity.AccountEntity;

import java.util.List;

public interface IAccountService<AccountDto> {

  AccountBasicDto findById(Long accountId);

  double calculateBalance(Long accountId);

  List<AccountDto> findAllByUser(Long userId);

  void updateBalance(Long accountId, Double amount);

  AccountEntity findEntityById (Long accountId);
}
