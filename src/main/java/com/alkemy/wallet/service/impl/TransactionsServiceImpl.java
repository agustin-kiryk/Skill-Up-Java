package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionsServiceImpl implements ITransactionService {

  @Autowired
  private ITransactionRepository ITransactionRepository;

  @Autowired
  private TransactionMap transactionMap;

  @Autowired
  private IUserService userService;

  @Autowired
  private IAccountRepository accountRepository;

  @Autowired
  private IUserRepository userRepository;

  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, String type) {

    List<TransactionEntity> entities = ITransactionRepository.findByAccountIdAndType(accountId,
        TypeTransaction.valueOf(type));

    List<TransactionDto> dtoList = transactionMap.transactionEntityList2DtoList(entities);

    return dtoList;
  }

  @Override
  public TransactionDto createTransaction(TransactionDto dto) {

    if (dto.getAmount() <= 0) {
      throw new ParamNotFound("The amount must be greater than 0");
    } else {
      TransactionEntity transactionEntity = transactionMap.transactionDto2Entity(dto);

      transactionEntity.setAmount(dto.getAmount());
      transactionEntity.setType(dto.getType());
      transactionEntity.setAccountId(accountRepository.getById(dto.getAccountDto().getId()));
      transactionEntity.setDescription(dto.getDescription());

      ITransactionRepository.save(transactionEntity);

      return dto;
    }
  }
  @Override
  public List<TransactionDto> transactionsById(Long userId) {

    UserDto user = userService.findById(userId);
    List<TransactionDto> dtoList = new ArrayList<>();
    List<AccountBasicDto> accounts = new ArrayList<>();

    for (AccountBasicDto account : accounts) {
      dtoList.add(account.getTransaction());
    }
    return dtoList;
  }
  @Override
  public TransactionDto getDetailById(Long transactionId) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Long userId = this.userRepository.findByEmail(email).getUserId();
    Optional<TransactionEntity> transaction = this.ITransactionRepository.findById(transactionId);
    if (!transaction.isPresent()){
      throw new ParamNotFound("id transaction invalid");
    }
    //TODO: FALTA VALIDAR SI EL USUARIO ES EL DUEÑO DEL ID DE TRANSACCION
    TransactionDto transactionDto = this.transactionMap.transactionEntity2Dto(transaction.get());
    return transactionDto;
  }

  @Override
  public TransactionDto send(TransactionDto transactionSendMoneyDto, Currency ars) {
    return null;
  }
}
