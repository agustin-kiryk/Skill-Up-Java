package com.alkemy.wallet.dto;

import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import lombok.Data;



  @Data
  public class AccountDTO {
    private double accounts;
    private Long id;
    private Currency currency;
    private Double transactionLimit;
    private Double balance;
    private Date updateDate;
    private Date creationDate;
    private final boolean softDelete = Boolean.FALSE;
    





  }
