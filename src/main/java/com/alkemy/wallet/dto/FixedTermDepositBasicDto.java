package com.alkemy.wallet.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedTermDepositBasicDto {

  private Long id;
  private double amount;
  private Date creationDate;
  private Date closingDate;
}
