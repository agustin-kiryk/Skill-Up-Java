package com.alkemy.wallet.dto;

import java.time.Instant;
import lombok.Data;



  @Data
  public class AccountDTO {
    private double accounts;
    private Integer id;
    private String currency;
    private Double transactionLimit;
    private Double balance;
    private Instant updateDate;
    private Instant creationDate;
    private final boolean softDelete = Boolean.FALSE;
  }
