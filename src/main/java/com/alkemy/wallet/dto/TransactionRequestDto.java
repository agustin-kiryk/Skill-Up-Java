package com.alkemy.wallet.dto;

import com.alkemy.wallet.enumeration.TypeTransaction;
import lombok.Data;

@Data
public class TransactionRequestDto {
  private Double amount;
  private String description;
  private Long accountId;
  private TypeTransaction typeTransaction;

}
