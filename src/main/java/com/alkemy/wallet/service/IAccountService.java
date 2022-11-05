package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountBasicDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

  AccountBasicDto findById(Long accountId);

  double calculateBalance(Long accountId);
}
