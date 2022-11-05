package com.alkemy.wallet.dto;


import com.alkemy.wallet.enumeration.Currency;
import lombok.Data;

@Data
public class AccountBasicDto {

  private Long accountId;
  private Currency currency;
  private double balance;
}



