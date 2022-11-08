package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.mapper.exception.AmountException;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private ITransactionRepository ITransactionRepository;

  @Autowired
  private TransactionMap transactionMap;

  @Autowired
  private IUserService userService;

  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, String type) {

    List<TransactionEntity> entities = ITransactionRepository.findByAccountIdAndType(accountId,
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
  public TransactionDto createNewDeposit(TransactionDto dto) {
    TransactionEntity deposit = transactionMap.transactionDto2Entity(dto);
    Double depositAmount = deposit.getAmount();

    if(depositAmount < 0){
      throw new AmountException("the amount must be greater than zero");
    }
    TransactionEntity createdDeposit = ITransactionRepository.save(deposit);
    return transactionMap.transactionEntity2Dto(createdDeposit);
  }
}
