package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;
import java.util.List;

public interface ITransactionService {

  List<TransactionDto> transactionsById(Long userId);

  public List<TransactionDto> getByAccountAndType(Long accountId, String type );


    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);
}
