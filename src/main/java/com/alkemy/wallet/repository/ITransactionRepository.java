package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.TransactionEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findAll(Specification<TransactionEntity> spec);

  List<TransactionEntity> findByAccountIdAndType(Long accountId, TypeTransaction type);
}
