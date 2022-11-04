package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.service.ITransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private TransactionRepository transactionRepository;


  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, String type) {

    List<TransactionEntity> entities = transactionRepository.findByAccountIdAndType(accountId, type);
    

    return null;
  }
}
