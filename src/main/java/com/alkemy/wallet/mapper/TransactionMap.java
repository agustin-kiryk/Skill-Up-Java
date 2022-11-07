package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TransactionMap {

  public TransactionDto transactionEntity2Dto(TransactionEntity entity) {
    TransactionDto transactionDto = new TransactionDto();

    transactionDto.setId(entity.getId());
    transactionDto.setTypeTransaction(entity.getType());//TODO: CAMBIO GET TYPE.
    transactionDto.setAmount(entity.getAmount());
    transactionDto.setDescription(entity.getDescription());
    transactionDto.setTransactionDate(entity.getTransactionDate());

    return transactionDto;

  }

  public List<TransactionDto> transactionEntityList2DtoList(List<TransactionEntity> entities) {
    List<TransactionDto> dtos = new ArrayList<>();

    for (TransactionEntity transactionEntity : entities) {

      dtos.add(transactionEntity2Dto(transactionEntity));
    }

    return dtos;
  }

  public void refreshValues(TransactionEntity transactionEntity, TransactionDto transactionDto) {

    transactionEntity.setAmount(transactionDto.getAmount());
    transactionEntity.setTransactionDate(transactionDto.getTransactionDate());
    transactionEntity.setType(transactionDto.getTypeTransaction());
    transactionEntity.setDescription(transactionDto.getDescription());
  }
}
