package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.auth.dto.AccountRequestDto;
import com.alkemy.wallet.auth.dto.AccountResponseDto;
import com.alkemy.wallet.dto.AccountBalanceDto;
import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.AccountDto;
import com.alkemy.wallet.dto.FixedTermDepositBasicDto;
import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.mapper.AccountMap;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IAccountService;
import com.alkemy.wallet.service.ITransactionService;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
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


    AccountBasicDto account = accountService.findById(accountId);
    List<TransactionDto> payments = transactionService.getByAccountAndType(accountId, "PAYMENT");
    List<TransactionDto> incomes = transactionService.getByAccountAndType(accountId, "INCOME");

    for (int i = 0; i < payments.size(); i++) {

      TransactionDto payment;
      payment = payments.get(i);

      totalPayment = totalPayment + payment.getAmount();

    }

    for (int i = 0; i < incomes.size(); i++) {

      TransactionDto income;
      income = incomes.get(i);

      totalIncome = totalIncome + income.getAmount();

    }

    if (account.getFixedTermDeposits() != null) {

      List<FixedTermDepositBasicDto> deposits = account.getFixedTermDeposits();

      for (int i = 0; i < deposits.size(); i++) {

        FixedTermDepositBasicDto fixedTermDeposit;
        fixedTermDeposit = deposits.get(i);

        fixedTermDeposits += fixedTermDeposit.getAmount();

      }
    }

    double balance = totalIncome - totalPayment - fixedTermDeposits;

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
  public AccountDto createAccount(String currency) {
    //encuentro el usuario que esta logeado
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = this.IUserRepository.findByEmail(email);
    List<AccountEntity> accounts = this.IAccountRepository.findAllByUser(user);
    if (user == null) {
      throw new ParamNotFound("ID invalid");
    }
    // comparo si tiene una cuenta con la misma currency
    accounts.forEach(account -> {
      if (accounts.stream()
          .anyMatch(i -> currency.equals(i.getCurrency().name()))) {
        throw new BadCredentialsException("The account of this currency already exists");
      }
    });
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setCurrency(Currency.valueOf(currency));
    accountEntity.setCreationDate(new Date());
    accountEntity.setBalance(0.0);
    if (currency.equals("ARS")) {
      accountEntity.setTransactionLimit(300000.00);
    } else {
      accountEntity.setTransactionLimit(1000.00);
    }
    accountEntity.setUpdateDate(new Date());

    AccountEntity entitySaved = this.IAccountRepository.save(accountEntity);
    AccountDto result = accountMap.accountEntity2DTO(entitySaved);

    return result;
  }



    private final IAccountRepository accountRepository;
    private final ITransactionRepository iTransactionRepository;

    @Override
    public AccountResponseDto save(AccountRequestDto request) {
      return null;
    }


    public AccountBalanceDto getAccountBalance(long idUser) {
      Optional<AccountEntity> account = accountRepository.findByUserId(idUser);
      if (account.isEmpty())
        return null;

      AccountBalanceDto accountBalanceDTO = new AccountBalanceDto();
      if (account.get().getCurrency().equals(Currency.ARS)) {
        accountBalanceDTO.setBalanceUSD(account.get().getBalance() / 282);
        accountBalanceDTO.setBalanceARS(account.get().getBalance());
      }

      if (account.get().getCurrency().equals(Currency.USD)) {
        accountBalanceDTO.setBalanceUSD(account.get().getBalance() * 282);
        accountBalanceDTO.setBalanceARS(account.get().getBalance());
      }

      LocalDate dateDB = LocalDate.of
          (account.get().getCreationDate().getYear(),
              account.get().getCreationDate().getMonth(),
              account.get().getCreationDate().getDay());

      Period duration = Period.between(dateDB, LocalDate.now());
      if (duration.getMonths() > 0){
        accountBalanceDTO.setFixedTermDeposit(account.get().getBalance() * (282 * duration.getMonths()));
      }

      return accountBalanceDTO;
    }


  }



