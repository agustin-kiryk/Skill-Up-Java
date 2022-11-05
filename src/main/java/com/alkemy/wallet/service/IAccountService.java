package com.alkemy.wallet.service;

import com.alkemy.wallet.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

  double calculateBalance(Long accountId);
}
