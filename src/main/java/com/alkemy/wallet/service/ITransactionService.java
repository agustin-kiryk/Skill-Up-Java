package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.FixedTermDepositDto;
import com.alkemy.wallet.dto.TransactionDto;
import java.util.List;

public interface ITransactionService {

  List<TransactionDto> transactionsById(Long userId);

  public List<TransactionDto> getByAccountAndType(Long accountId, String type );

  TransactionDto createNewDeposit(TransactionDto dto);

}
