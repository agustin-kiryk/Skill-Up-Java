package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Long, TransactionEntity> {

}
