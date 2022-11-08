package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;
import java.util.Currency;
import java.util.List;
import javax.transaction.Transactional;

public interface ITransactionService {


  TransactionDto save(TransactionDto transaction);

  public List<TransactionDto> getAllByUser(long userId);


  @Transactional
  public TransactionDto edit(long userId, long id, String description);


  static TransactionDto send(TransactionDto transactionDto, com.alkemy.wallet.enumeration.Currency ars) {
    return transactionDto;
  }

  List<TransactionDto> transactionsById(Long userId);


  public List<TransactionDto> getByAccountAndType(Long accountId, String type );


    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);
}
