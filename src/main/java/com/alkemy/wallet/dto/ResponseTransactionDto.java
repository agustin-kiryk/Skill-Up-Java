package com.alkemy.wallet.dto;

import com.alkemy.wallet.entity.AccountEntity;
import com.alkemy.wallet.enumeration.TypeTransaction;
import java.sql.Timestamp;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTransactionDto {
  private Long id;
  private Double amount;
  private TypeTransaction type;
  private String description;

  private Long accountId;
  private AccountEntity account;
  private Timestamp transactionDate;


}
