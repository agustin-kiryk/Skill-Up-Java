package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.service.ITransactionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private TransactionMap transactionMap;


  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, String type) {

    List<TransactionEntity> entities = transactionRepository.findByAccountIdAndType(accountId,
        TypeTransaction.valueOf(type));

    List<TransactionDto> dtoList = transactionMap.transactionEntityList2DtoList(entities);

    return dtoList;
  }
}
