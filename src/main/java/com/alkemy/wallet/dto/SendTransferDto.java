package com.alkemy.wallet.dto;

import lombok.Data;

@Data

public class SendTransferDto {
  private Double amount;
  private String description;
  private Long destinationAccountId;

}
