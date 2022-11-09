package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.impl.SendMoneyImpl;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface ITransactionService{

  String moneySendInPesos(Long idUser, Long typeTransaction, Double amount);

  String moneySendInUsd(Long idUser, Long typeTransaction, Double amount);

  @Service
  public class TransactionService {

    private final ITransactionRepository repository;
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final SendMoneyImpl sendMoney;

    public TransactionService(ITransactionRepository repository, IUserRepository userRepository,
        IAccountRepository accountRepository, SendMoneyImpl sendMoney) {
      this.repository = repository;
      this.userRepository = userRepository;
      this.accountRepository = accountRepository;
      this.sendMoney = sendMoney;
    }


    public String moneySendInPesos(Long idUser, Long idTargetUser, Double amount) {
      return sendMoney.sendMoney(idUser, idTargetUser, amount, "peso Argentino(ARS)", 1, repository,
          userRepository, accountRepository);
    }

    public String moneySendInUsd(Long idUser, Long idTargetUser, Double amount) {
      return sendMoney.sendMoney(idUser, idTargetUser, amount, "dolar Estadounidense(USD)", 2,
          repository, userRepository, accountRepository);
    }
  }

  List<TransactionDto> transactionsById(Long userId);

  public List<TransactionDto> getByAccountAndType(Long accountId, String type);

  TransactionDto createTransaction(TransactionDto dto);


  TransactionDto getDetailById(Long transactionId);

  TransactionDto send(TransactionDto transactionSendMoneyDto, Currency ars);}



