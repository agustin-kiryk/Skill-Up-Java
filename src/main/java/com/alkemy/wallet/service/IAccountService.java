package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.entity.AccountEntity;

import java.util.List;

public interface IAccountService {

  AccountBasicDto findById(Long accountId);

  double calculateBalance(Long accountId);

  List<AccountDTO> findAllByUser(Long userId);

  void updateBalance(Long accountId, Double amount);

  AccountEntity findEntityById (Long accountId);
}
