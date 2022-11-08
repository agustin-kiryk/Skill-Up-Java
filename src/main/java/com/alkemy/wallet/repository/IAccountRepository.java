package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.enumeration.Currency;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity , Long> {


  static void save() {
    
  }




  AccountEntity findByCurrencyAndUser(Currency currency, User user);

  List<AccountEntity> findAllByUser(UserEntity user);
  
  AccountEntity findByAccountId(Long accountId);
}
