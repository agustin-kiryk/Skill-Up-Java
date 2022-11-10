package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.exception.AmountException;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    Optional<TransactionEntity> transaction = this.ITransactionRepository.findById(transactionId);
    if (!transaction.isPresent()) {
      throw new ParamNotFound("id transaction invalid");
    }
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByEmail(email);
    if (!Objects.equals(user.getUserId(), transaction.get().getUserEntity().getUserId())) {
      throw new ParamNotFound("the Transaction id don't below to user");
    }

    TransactionDto transactionDto = this.transactionMap.transactionEntity2Dto(transaction.get());
    return transactionDto;
  }

  @Override
  public TransactionDto refreshValues(Long id, TransactionDto transactionDto) {
    Optional<TransactionEntity> transaction = ITransactionRepository.findById(id);
    if (!transaction.isPresent()) {
      throw new ParamNotFound("Transaction Id not found");
    }

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByEmail(email);
    if (!Objects.equals(user.getUserId(), transaction.get().getUserEntity().getUserId())) {
      throw new ParamNotFound("the Transaction id don't below to user");
    }
    transactionMap.updateDescription(transaction, transactionDto.getDescription());
    TransactionEntity transactionUpdated = ITransactionRepository.save(transaction.get());
    return transactionMap.transactionEntity2Dto(transactionUpdated);

  }

  @Override
  public TransactionDto createNewDeposit(TransactionDto dto) {
    TransactionEntity deposit = transactionMap.transactionDto2Entity(dto);
    Double depositAmount = deposit.getAmount();

    if (depositAmount < 0) {
      throw new AmountException("the amount must be greater than zero");
    }
    TransactionEntity createdDeposit = ITransactionRepository.save(deposit);
    return transactionMap.transactionEntity2Dto(createdDeposit);

  }
}
