package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.*;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.exception.AmountException;
import com.alkemy.wallet.mapper.TransactionMap;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.service.IUserService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

  @Autowired
  private IAccountService accountService;

  @Override
  public List<TransactionDto> getByAccountAndType(Long accountId, TypeTransaction type) {

    AccountEntity account = accountRepository.findByAccountId(accountId);

    List<TransactionEntity> entities = ITransactionRepository.findAllByAccountIdAndType(account, type);

    List<TransactionDto> dtoList = transactionMap.transactionEntityList2DtoList(entities);

    return dtoList;
  }

  @Override
  public TransactionDto createTransaction(TransactionDto dto) {

    if (dto.getAmount() <= 0) {
      throw new ParamNotFound("The amount must be greater than 0");
    } else {
      TransactionEntity transactionEntity = transactionMap.transactionDto2Entity(dto);
      AccountEntity accountEntity = accountRepository.findByAccountId(dto.getAccountId());


      transactionEntity.setAmount(dto.getAmount());
      transactionEntity.setType(dto.getType());
      transactionEntity.setAccountId(accountEntity);
      transactionEntity.setUser(accountEntity.getUser());
      transactionEntity.setDescription(dto.getDescription());
      transactionEntity.setTransactionDate(new Date());
      this.accountService.updateBalance(dto.getAccountId(),dto.getAmount());



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
    if (!Objects.equals(user.getUserId(), transaction.get().getUser().getUserId())) {
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
    if (!Objects.equals(user.getUserId(), transaction.get().getUser().getUserId())) {
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

  @Override
  public TransactionDto send(SendTransferDto sendTransferDto,Currency currency) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository.findByEmail(email);
    AccountEntity account = this.accountRepository.findByCurrencyAndUser(currency, user);
    AccountEntity receive = accountRepository.findById(sendTransferDto.getDestinationAccountId()).orElseThrow(
        ()->new ParamNotFound("the destination account does not exist"));
    if(user == null || account == null || receive == null){
      throw new ParamNotFound("invalid operation");
    }
    if (sendTransferDto.getAmount() <= 0){
      throw new ParamNotFound("Amount must be greater than 0");
    }
    if (sendTransferDto.getAmount() > account.getTransactionLimit()) {
      throw new ParamNotFound("Amount must be less than the limit");
    }
    if (receive.getCurrency() != currency){
      throw new ParamNotFound("the destination account has a different currency");
    }
    if (account.getBalance() < sendTransferDto.getAmount()){
      throw new ParamNotFound("insufficient funds");

    }
    TransactionDto send = new TransactionDto();
    send.setAmount(sendTransferDto.getAmount());
    send.setDescription(sendTransferDto.getDescription());
    send.setAccountId(account.getAccountId());
    send.setType(TypeTransaction.PAYMENT);
    send.setTransactionDate(new Date());
    TransactionDto transactionDto = createTransaction(send);

    TransactionDto reciver = new TransactionDto();
    reciver.setAmount(sendTransferDto.getAmount());
    reciver.setDescription(sendTransferDto.getDescription());
    reciver.setAccountId(receive.getAccountId());
    reciver.setType(TypeTransaction.INCOME);
    reciver.setTransactionDate(new Date());
    createTransaction(reciver);

    return transactionDto;
  }

  @Override
  public PageDto<TransactionDto> findAllTransaction(Pageable page, HttpServletRequest request, Long id) {
    PageDto<TransactionDto> pageDto = new PageDto<>();
    Map<String,String> links = new HashMap<>();
    List<TransactionDto> listDto = new ArrayList<>();

    UserEntity user = userRepository.findByUserId(id);

    Page<TransactionEntity> elements =  ITransactionRepository.findAllByUser(user,page);

    elements.getContent().forEach(element -> listDto.add(transactionMap.transactionEntity2Dto(element)));
    links.put("next",elements.hasNext()?makePaginationLink(request,page.getPageNumber()+1):"");
    links.put("previous",elements.hasPrevious()?makePaginationLink(request,page.getPageNumber()-1):"");

    pageDto.setContent(listDto);
    pageDto.setLinks(links);

    return pageDto;
  }
  private String makePaginationLink(HttpServletRequest request, int page) {
    return String.format("%s?page=%d", request.getRequestURI(), page);
  }

}
