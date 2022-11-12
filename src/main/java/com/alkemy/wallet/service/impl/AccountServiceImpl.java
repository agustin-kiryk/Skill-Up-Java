package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.dto.*;
import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.CurrencyDto;
import com.alkemy.wallet.dto.FixedTermDepositBasicDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.FixedTermDepositEntity;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.enumeration.TypeTransaction;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.mapper.AccountMap;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import com.sun.jdi.Value;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class AccountServiceImpl implements IAccountService {

  @Autowired
  private AccountMap accountMap;
  @Autowired
  private IAccountRepository IAccountRepository;
  @Autowired
  private ITransactionService transactionService;
  @Autowired
  private IUserRepository IUserRepository;
  @Autowired
  private IAccountService accountService;
  @Autowired
  private ITransactionRepository transactionRepository;


  @Override
  public AccountBasicDto findById(Long accountId) {
    return accountMap.accountEntity2BasicDto(IAccountRepository.findByAccountId(accountId));
  }

  //The total balance of an account its the remainder of all the incomes minus all the payments and fixed term deposits.
  //So this method gets all the incomes by a query and calculates the total, same with the payments.
  //Then checks out if theres any fixed term deposits, in case positive calculates the total of those as well.
  //At the end the subtraction is done, giving you the total balance of the account;
  @Override
  public double calculateBalance(Long accountId) {

    double totalPayment = 0;
    double totalIncome = 0;
    double fixedTermDeposits = 0;


    AccountEntity account = IAccountRepository.findByAccountId(accountId);
    List<TransactionEntity> payments = transactionRepository.findAllByAccountIdAndType(account,
        TypeTransaction.PAYMENT);
    List<TransactionEntity> incomes = transactionRepository.findAllByAccountIdAndType(account, TypeTransaction.INCOME);

    for (int i = 0; i < payments.size(); i++) {

      TransactionEntity payment;
      payment = payments.get(i);

      totalPayment = totalPayment + payment.getAmount();

    }

    for (int i = 0; i < incomes.size(); i++) {

      TransactionEntity income;
      income = incomes.get(i);

      totalIncome = totalIncome + income.getAmount();

    }

    if (account.getFixedTermDeposits() != null) {

      List<FixedTermDepositEntity> deposits = account.getFixedTermDeposits();

      for (int i = 0; i < deposits.size(); i++) {

        FixedTermDepositEntity fixedTermDeposit;
        fixedTermDeposit = deposits.get(i);

        fixedTermDeposits += fixedTermDeposit.getAmount();

      }
    }

    double balance = totalIncome - totalPayment - fixedTermDeposits;

    account.setBalance(balance);
    IAccountRepository.save(account);
    return balance;
  }

  @Override
  public List<AccountDto> findAllByUser(Long userId) {
    UserEntity entity = IUserRepository.findById(userId).orElseThrow(
        ()-> new ParamNotFound("User ID Invalid"));
    List<AccountEntity> accounts = IAccountRepository.findAllByUser(entity);
    List<AccountDto> accountsList = accountMap.accountEntityList2DtoList(accounts);

    return accountsList;
  }



  @Override
  public void updateBalance(Long accountId, Double amount) {
    Optional<AccountEntity> accountEntity= IAccountRepository.findById(accountId);
    if (!accountEntity.isPresent()){
      throw new ParamNotFound("No id macth");
    }
    accountEntity.get().setBalance(accountEntity.get().getBalance()-amount);
    IAccountRepository.save(accountEntity.get());
  }

  @Override
  public AccountEntity createAccount(CurrencyDto currencyDto) {

    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setCurrency(currencyDto.getCurrency());
    accountEntity.setCreationDate(new Date());
    accountEntity.setBalance(0.0);
    if (currencyDto.getCurrency().equals(Currency.ARS)) {
      accountEntity.setTransactionLimit(300000.00);
    } else {
      accountEntity.setTransactionLimit(1000.00);
    }
    accountEntity.setUpdateDate(new Date());
    return accountEntity;
  }

  @Override
  public AccountDto updateAccount(Long id,Double transactionLimitUpdated) {

    AccountEntity accountEntity=IAccountRepository.findById(id).orElseThrow(
        ()-> new ParamNotFound("Account ID Invalid"));
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = this.IUserRepository.findByEmail(email);
    List<AccountEntity> userAccounts = this.IAccountRepository.findAllByUser(user);
    if (!userAccounts.contains(accountEntity))
      throw new ParamNotFound("The Account doesn't match with the User");
    accountEntity.setTransactionLimit(transactionLimitUpdated);
    accountEntity.setUpdateDate(new Date());
    AccountEntity entitySaved=IAccountRepository.save(accountEntity);
    AccountDto result=accountMap.accountEntity2DTO(entitySaved);

    return result;
  }

  @Override
  public String addAccount(String email, CurrencyDto currencyDto) {
    //encuentro el usuario que esta logeado
    String Useremail = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = this.IUserRepository.findByEmail(email);
    List<AccountEntity> accounts = this.IAccountRepository.findAllByUser(user);
    if (user == null) {
      throw new ParamNotFound("ID invalid");
    }
    // comparo si tiene una cuenta con la misma currency
    accounts.forEach(account -> {
      if (accounts.stream()
          .anyMatch(i -> currencyDto.getCurrency().equals(i.getCurrency()))) {
        throw new BadCredentialsException("The account of this currency already exists");
      }
    });
    AccountEntity account = createAccount(currencyDto);
    UserEntity log = this.IUserRepository.findByEmail(email);
    account.setUser(log);
    this.IAccountRepository.save(account);
    log.addAccount(account);
    IUserRepository.save(log);
    return HttpStatus.CREATED.getReasonPhrase();

  }


  @Override
  public PageDto<AccountDto> findAllAccounts(Pageable page, HttpServletRequest request) {
    PageDto<AccountDto> pageDto = new PageDto<>();
    Map<String,String> links = new HashMap<>();
    List<AccountDto> listDtos = new ArrayList<>();
    Page<AccountEntity> elements = IAccountRepository.findAll(page);

    elements.getContent().forEach(element -> listDtos.add(accountMap.accountEntity2DTO(element)));
    links.put("next",elements.hasNext()?makePaginationLink(request,page.getPageNumber()+1):"");
    links.put("previous",elements.hasPrevious()?makePaginationLink(request,page.getPageNumber()-1):"");

    pageDto.setContent(listDtos);
    pageDto.setLinks(links);

    return pageDto;
  }
  private String makePaginationLink(HttpServletRequest request, int page) {
    return String.format("%s?page=%d", request.getRequestURI(), page);
  }
}
