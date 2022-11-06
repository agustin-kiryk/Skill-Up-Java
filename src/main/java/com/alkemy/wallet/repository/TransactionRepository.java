package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionsEntity,Long> {

}
