package com.alkemy.wallet.dto;

import java.util.Date;
import lombok.Data;

@Data
public class TransactionDto {

  private Long userId;

  private Long accountId;

  private String type;

  private double amount;

  private String description;

  private Date transactionDate;


}
