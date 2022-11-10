package com.alkemy.wallet.mapper;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.TransactionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMap {
  @Autowired
  private AccountMap accountMap;

  public TransactionDto transactionEntity2Dto(TransactionEntity entity) {
    TransactionDto transactionDto = new TransactionDto();

    transactionDto.setId(entity.getId());
    transactionDto.setType(entity.getType());//TODO: CAMBIO GET TYPE.
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

  public TransactionEntity transactionDto2Entity(TransactionDto dto){

    TransactionEntity entity = new TransactionEntity();

    entity.setId(dto.getId());
    entity.setType(dto.getType());
    entity.setAmount(dto.getAmount());
    entity.setDescription(dto.getDescription());
    entity.setTransactionDate(dto.getTransactionDate());
    entity.setAccountId(accountMap.accountDto2Entity(dto.getAccountDto()));

    return entity;
  }

    public void updateDescription(Optional<TransactionEntity> transaction, String description) {

      transaction.get().setDescription(description);
    }
}
