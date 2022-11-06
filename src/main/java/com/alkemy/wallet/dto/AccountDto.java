package com.alkemy.wallet.dto;


import com.alkemy.wallet.enumeration.Currency;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

  private Long id;
  private Currency currency;
  private Double transactionLimit;
  private Double balance;
  private Date updateDate;
  private Date creationDate;
  private TransactionDto transaction;

}
