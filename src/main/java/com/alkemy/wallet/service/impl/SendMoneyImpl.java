package com.alkemy.wallet.service.impl;

import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.ITransactionRepository;
import com.alkemy.wallet.repository.IUserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SendMoneyImpl {

  private String specificTypeOfMoney(int typeMoney, String money, AccountEntity accountUser, AccountEntity accountTargetUser) {
    String error = "Error solo puede enviar dinero en ";
    if (typeMoney == 1 && (!accountUser.getCurrency().equals(Currency.ARS) || !accountTargetUser.getCurrency().equals(Currency.ARS)))
      return error + money;
    else {
      if (typeMoney == 2 && (!accountUser.getCurrency().equals(Currency.USD) || !accountTargetUser.getCurrency().equals(Currency.USD)))
        return error + money;
    }
    return null;
  }

  public String sendMoney(Long idUser, Long idTargetUser, Double amount, String money,int typeMoney, ITransactionRepository
      repository, IUserRepository userRepository, IAccountRepository accountRepository) {

    String noDisponible = " no esta disponible";
    if (idTargetUser.equals(idUser))
      return "Error no se puede enviar dinero al mismo usuario";

    Optional<UserEntity> user = userRepository.findById(idUser);
    if (user.isEmpty())
      return "El usuario con id " + idUser + noDisponible;

    Optional<UserEntity> targetUser = userRepository.findById(idTargetUser);
    if (targetUser.isEmpty())
      return "El usuario con id " + idTargetUser + noDisponible;

    Optional<AccountEntity> accountUser = accountRepository.findByFkUserId(idUser);
    if (accountUser.isEmpty())
      return "La account con id " + idUser + noDisponible;

    Optional<AccountEntity> accountTargetUser = accountRepository.findByFkUserId(idTargetUser);
    if (accountTargetUser.isEmpty())
      return "La account con id " + idTargetUser + noDisponible;

    String error = specificTypeOfMoney(typeMoney, money, accountUser.get(), accountTargetUser.get());
    if (error != null)
      return error;

    if (accountUser.get().getBalance() < amount)
      return "Error valor disponible superado";

    if (amount > accountUser.get().getTransactionLimit())
      return "Error supera el limite de transacciones";

    double balanceUser = accountUser.get().getBalance() - amount;
    double targetUserBalance = accountTargetUser.get().getBalance() + amount;

    accountUser.get().setBalance((balanceUser));
    accountTargetUser.get().setBalance(targetUserBalance);

    TransactionEntity transaction = new TransactionEntity();
    //null, amount, TransactionTypeEnum.INCOME,
    //       "Transacción exitosa", idUser, idTargetUser, LocalDateTime.now(), null, null);

    accountRepository.save(accountTargetUser.get());
    accountRepository.save(accountUser.get());
    repository.save(transaction);

    return "Operación realizada exitosamente";
  }
}
