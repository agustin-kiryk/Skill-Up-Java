package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity , Long> {
  List<AccountEntity> findAllByUser(UserEntity user);

  AccountEntity findByAccountId(Long accountId);
}
