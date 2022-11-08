package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import com.alkemy.wallet.repository.IAccountRepository;
import com.alkemy.wallet.repository.IUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transaction;
import org.springframework.http.ResponseEntity;


public interface ITransactionService {

  static <TransactionDTO> ResponseEntity<Object> sendArs(TransactionDTO transactionDTO) {
    return null;
  }

  static TransactionDto send(TransactionDto transactionDto, Currency ars) {
    return transactionDto;
  }
  List<TransactionDto> transactionsById(Long userId);

  public List<TransactionDto> getByAccountAndType(Long accountId, String type );



  Object getTransactionDetailById(Integer id);

  Object getTransactions(Integer userId);




  public default String SendArs(Long idUser, Long idTargetUser, Double amount) {
    if (idTargetUser.equals(idUser))
      return "Error no se puede hacer una transferencia al mismo usuario";

    public boolean validateToken(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    UserEntity user = IUserRepository.findByUserId(idUser);
    if (user.isEmpty())
      return "El usuario con id " + idUser + " no esta disponible";

    Optional<accounts> accountUser = IAccountRepository.findByAccountId(idUser);
    if (accountUser.isEmpty())
      return "La account con id " + idUser + " no esta disponible";

    Optional<Accounts> accountTargetUser = IAccountRepository.findByAccountId(idTargetUser);
    if (accountTargetUser.isEmpty())
      return "La account con id " + idTargetUser + " no esta disponible";

    if (!accountUser.get().getCurrency().equals(accountTargetUser.get().getCurrency()))
      return "Error solo puede enviar dinero en peso Argentino(ARS)";

    if (accountUser.get().getBalance() < amount)
      return "Error valor disponible superado";

    if (amount > accountUser.get().getTransactionLimit())
      return "Error supera el limite de transacciones";

    double balanceUser = accountUser.get().getBalance() - amount;
    double targetUserBalance = accountTargetUser.get().getBalance() + amount;

    accountUser.get().setBalance((balanceUser));
    accountTargetUser.get().setBalance(targetUserBalance);

    Transaction transaction = new Transaction(null, amount, TransactionTypeEnum.INCOME,
        "Transacción exitosa", idUser, idTargetUser, LocalDateTime.now(), null, null);

    IAccountRepository.save(accountTargetUser.get());
    IAccountRepository.save(accountUser.get());
    repository.save(transaction);

    return "Operación realizada exitosamente";
  }
}