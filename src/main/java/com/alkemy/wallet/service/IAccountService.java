package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.AccountBasicDto;
import java.util.List;

public interface IAccountService {

  AccountBasicDto findById(Long accountId);

  double calculateBalance(Long accountId);

  List<AccountDto> findAllByUser(Long userId);
}
