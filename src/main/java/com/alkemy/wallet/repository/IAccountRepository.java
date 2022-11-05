package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

  @Repository
  public interface IAccountRepository extends JpaRepository<AccountEntity, Long>{

    <user> List<AccountEntity> findAllByUser(user user);

  }

