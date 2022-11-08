package com.alkemy.wallet.dto;


import com.alkemy.wallet.enumeration.Currency;
import java.util.List;
import lombok.Data;

@Data
public class AccountBasicDto {

  private Long accountId;
  private Currency currency;
  private double balance;
  private List<FixedTermDepositBasicDto> fixedTermDeposits;
  private TransactionDto transaction;
}



