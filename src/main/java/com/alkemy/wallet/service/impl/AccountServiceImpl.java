package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.FixedTermDepositBasicDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.exception.ParamNotFound;
import com.alkemy.wallet.mapper.AccountMap;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.repository.UserRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

  @Autowired
  private AccountMap accountMap;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private ITransactionService transactionService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private IAccountService accountService;


  @Override
  public AccountBasicDto findById(Long accountId) {
    return accountMap.accountEntity2BasicDto(accountRepository.findByAccountId(accountId));
  }

  //The total balance of an account its the remainder of all the incomes minus all the payments and fixed term deposits.
  //So this method gets all the incomes by a query and calculates the total, same with the payments.
  //Then checks out if theres any fixed term deposits, in case positive calculates the total of those as well.
  //At the end the subtraction is done, giving you the total balance of the account;
  @Override
  public double calculateBalance(Long accountId) {

    double totalPayment = 0;
    double totalIncome = 0;
    double fixedTermDeposits = 0;


    AccountBasicDto account = accountService.findById(accountId);
    List<TransactionDto> payments = transactionService.getByAccountAndType(accountId, "PAYMENT");
    List<TransactionDto> incomes = transactionService.getByAccountAndType(accountId, "INCOME");

    for (int i = 0; i < payments.size(); i++) {

      TransactionDto payment;
      payment = payments.get(i);

      totalPayment = totalPayment + payment.getAmount();

    }

    for (int i = 0; i < incomes.size(); i++) {

      TransactionDto income;
      income = incomes.get(i);

      totalIncome = totalIncome + income.getAmount();

    }

    if (account.getFixedTermDeposits() != null) {

      List<FixedTermDepositBasicDto> deposits = account.getFixedTermDeposits();

      for (int i = 0; i < deposits.size(); i++) {

        FixedTermDepositBasicDto fixedTermDeposit;
        fixedTermDeposit = deposits.get(i);

        fixedTermDeposits += fixedTermDeposit.getAmount();

      }
    }

    double balance = totalIncome - totalPayment - fixedTermDeposits;

    return balance;
  }

  @Override
  public List<AccountDto> findAllByUser(Long userId) {
    UserEntity entity = userRepository.findById(userId).orElseThrow(
        ()-> new ParamNotFound("User ID Invalid"));
    List<AccountEntity> accounts = accountRepository.findAllByUser(entity);
    List<AccountDto> accountsList = accountMap.accountEntityList2DtoList(accounts);

    return accountsList;
  }
}
