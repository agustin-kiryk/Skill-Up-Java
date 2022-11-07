package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.exception.ParamNotFound;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private ITransactionRepository transactionRepository;

  @Autowired
  private TransactionMap transactionMap;

  @Autowired
  private IUserService userService;

  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, String type) {

    List<TransactionEntity> entities = transactionRepository.findByAccountIdAndType(accountId,
        TypeTransaction.valueOf(type));

    List<TransactionDto> dtoList = transactionMap.transactionEntityList2DtoList(entities);

    return dtoList;
  }

  @Override
  public List<TransactionDto> transactionsById(Long userId) {
    UserDto user=userService.findById(userId);
    List<TransactionDto> dtoList=null;
    for (int i=0;i<user.getAccounts().size();i++)
      dtoList.add(user.getAccounts().get(i).getTransaction());
    return dtoList;
  }

  @Override
  public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!authentication.isAuthenticated()){
      throw new ParamNotFound("User not logged in");
    }

    Optional<TransactionEntity> transactionEntity = transactionRepository.findById(id);
    if (!transactionEntity.isPresent()){
      throw new ParamNotFound("Transaction ID not found");
    }

    transactionMap.refreshValues(transactionEntity.get(),transactionDto);
    TransactionEntity transactionUpdate = transactionRepository.save(transactionEntity.get());

    return transactionMap.transactionEntity2Dto(transactionUpdate);
  }


}
