package com.alkemy.wallet.dto;

import com.alkemy.wallet.enumeration.TypeTransaction;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
  private Long id;
  private Double amount;
  private TypeTransaction typeTransaction;
  private String description;
  private Date transactionDate;
}
