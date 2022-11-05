package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.service.IAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImpl implements IAccountService {

  @Autowired
  private TransactionRepository transactionRepository;


  @Override
  public double calculateBalance(Long accountId) {

    double totalPayment = 0;
    double totalIncome = 0;
    double balance = totalIncome - totalPayment;

    List<TransactionEntity> payments = transactionRepository.findByAccountIdAndType(accountId,
        "payment");
    List<TransactionEntity> incomes = transactionRepository.findByAccountIdAndType(accountId,
        "income");

    for (int i = 0; i < payments.size(); i++) {

      TransactionEntity payment;
      payment = payments.get(i);

      totalPayment = totalPayment + payment.getAmount();

    }

    for (int i = 0; i < incomes.size(); i++) {

      TransactionEntity income;
      income = incomes.get(i);

      totalIncome = totalIncome + income.getAmount();

    }

    return balance;
  }
}
