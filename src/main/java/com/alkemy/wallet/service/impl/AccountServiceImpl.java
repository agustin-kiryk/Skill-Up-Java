package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImpl implements IAccountService {

  @Autowired
  private ITransactionService transactionService;


  @Override
  public double calculateBalance(Long accountId) {

    double totalPayment = 0;
    double totalIncome = 0;
    double balance = totalIncome - totalPayment;

    List<TransactionDto> payments = transactionService.getByAccountAndType(accountId,
        "payment");
    List<TransactionDto> incomes = transactionService.getByAccountAndType(accountId,
        "income");

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

    return balance;
  }
}
