package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.TransactionsDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.TransactionsEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private TransactionMap transactionMap;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private IUserService userService;
  @Autowired
  private IAccountService accountService;

  @Override
  public List<TransactionsDto> transactionsById(Long userId) {
    UserDto user=userService.findById(userId);
    List<TransactionsDto> dtoList=null;
    for (int i=0;i<user.getAccounts().size();i++)
      dtoList.add(user.getAccounts().get(i).getTransaction());
    return dtoList;
  }
}
